# Cactoos-Cache

## Introduction

Cactoos-Cache is a truly object-oriented implementation of caching.

**Motivation**.
We are not happy with many existing caching APIs because they are procedural
rather than object-oriented. They do their job, but mostly through strongly
coupled classes and static methods. Cactoos-Cache proposes doing almost exactly
the same thing, but through (more Object-Oriented) objects.

**Principles**.
These are the [design principles](http://www.elegantobjects.org#principles)
behind Cactoos-Cache.

## Requirements

- Java 17+
- [Cactoos](https://www.cactoos.org) as a dependency (that's it)

## How to use

- Maven

Add to your `pom.xml` file:

```xml
<dependency>
  <groupId>com.github.fabriciofx</groupId>
  <artifactId>cactoos-cache</artifactId>
  <version>0.0.9</version>
</dependency>
```

- Gradle

Add to your `build.gradle` file:

```groovy
dependencies {
    implementation "com.github.fabriciofx:cactoos-cache:0.0.9"
}
```

## Terminology

A `Key` is composed of a value (which must implement `Bytes`) and a hash. An
`Entry` is the triple consisting of a `Key`, a value (which you want to
associate with the `Key`) and metadata. Metadata can be anything (as `String`s),
as SQL tables names (which are used for cache invalidation). A `Store` is
responsible for maintaining the association `Key` -> `Entry`. Using a `Store`,
you can:

- Retrieve an `Entry` using a `Key`;
- Associate (save) a `Key` with an `Entry`;
- Delete an association by `Key`;
- Check whether an association `Key` -> `Entry` exists;
- Retrieve all `Key`s;
- Retrieve all `Entry`s.

A `Cache` is responsible for providing a `Store` and generating `Statistics`
about `Store` usage. The most common statistics are:

- *Insertions*: number of times a value is inserted in the cache;
- *Replacements*: number of time a value is replaced in the cache;
- *Hits*: number of times a value was found in the cache;
- *Misses*: number of times a value was not found in the cache;
- *Lookups*: total number of accesses (hits + misses);
- *Invalidations*: explicit cache removals;
- *Evictions*: automatic cache removals.

These statistics are already provided, but you can create your own if needed.

## Usage

Suppose you want to create a cache for a set of words, where each word is
associated with a list of synonyms. For that, you need to create a `Word` that
implements `Bytes`:

```java
public final class Word implements Bytes {
    private final String content;

    public Word(final String content) {
        this.content = content;
    }

    @Override
    public byte[] asBytes() throws Exception {
        return this.content.getBytes(StandardCharsets.UTF_8);
    }
}
```

And now, we use `CacheOf`, `KeyOf` and `EntryOf` to provide store, retrieve,
check if contains or delete from the cache:

```java
// Create a new cache (associate a Word to List<String>)
final Cache<Word, List<String>> cache = new CacheOf<>();

// Store
cache.store().save(
    new KeyOf<>(new Word("a")),
    new EntryOf<>(
        new KeyOf<>(new Word("a")),
        new ListOf<>("x", "y", "z")
    )
);

// Store
cache.store().save(
    new KeyOf<>(new Word("b")),
    new EntryOf<>(
        new KeyOf<>(new Word("b")),
        new ListOf<>("k", "l", "m")
    )
);

// Check if contains (it's false)
final boolean exists = cache.store().contains(new KeyOf<>(new Word("c")));

// Retrieve
final List<String> words = cache
    .store()
    .retrieve(new KeyOf<>(new Word("a")))
    .value();

// Delete
final List<String> deleted = cache
    .store()
    .delete(new KeyOf<>(new Word("a")))
    .value();
```

### Cache eviction

Cache eviction is an *automatic* entry removal performed by a `Policy`. To
enable it, just compose the cache with the `Policed` decorator and pass the
policies that the enforcer will execute, as in the example below:

```java
// Create a cache with Expired and MaxSize policies
final Cache<Word, List<String>> cache = new Policed<>(
    new CacheOf<>(),
    new ExpiredPolicy<>(),
    new MaxSizePolicy<>()
);

// Normal cache usage
```

By default, `Cactoos-Cache` will use the `DelayedEnforcer` that iterates the cache,
applying the policies to each entry every **500 milliseconds**. This time can be
changed too; just create a new `DelayedEnforcer` with the desired time:

```java
final Cache<Word, List<String>> cache = new Policed<>(
    new CacheOf<>(),
    new DelayedEnforcer<>(1L, TimeUnit.SECONDS), // Execute at each 1 second
    new ExpiredPolicy<>(),
    new MaxSizePolicy<>()
);

// Normal cache usage
```

### Policies

`Cactoos-Cache` has the following policies:

- `MaxSizePolicy`: remove entries when reach a max number
- `ExpiredPolicy`: remove entries when reach an expired lifetime

To use `MaxSizePolicy` just use the policy using the max number of entries
(the default is `Integer.MAX_VALUE`), as the example below:

```java
// Create a cache with max 2 entries
final Cache<Word, List<String>> cache = new Policed<>(
    new CacheOf<>(),
    new MaxSizePolicy<>(2)
);

// Store
cache.store().save(
    new KeyOf<>(new Word("a")),
    new EntryOf<>(
        new KeyOf<>(new Word("a")),
        new ListOf<>("x", "y", "z")
    )
);

// Store
cache.store().save(
    new KeyOf<>(new Word("b")),
    new EntryOf<>(
        new KeyOf<>(new Word("b")),
        new ListOf<>("k", "l", "m")
    )
);

// Store this entry, but remove the first because the max number of entries
// is 2.
cache.store().save(
    new KeyOf<>(new Word("c")),
    new EntryOf<>(
        new KeyOf<>(new Word("c")),
        new ListOf<>("i", "j", "k")
    )
);
```

To use `ExpiredPolicy` create a new entry with `expiration` metadata using the
`LocalDateTime` object as lifetime, for example:

```java
// Create a cache with entries lifetime
final Cache<Word, List<String>> cache = new Policed<>(
    new CacheOf<>(),
    new ExpiredPolicy<>()
);

// Store an entry with lifetime of 5 seconds
cache.store().save(
    new KeyOf<>(new Word("a")),
    new EntryOf<>(
        new KeyOf<>(new Word("a")),
        new ListOf<>("x", "y", "z"),
        new MetadataOf()
            .with("expiration", LocalDateTime.now().plusSeconds(5L))
    )
);

// Store an entry with lifetime of 10 seconds
cache.store().save(
    new KeyOf<>(new Word("b")),
    new EntryOf<>(
        new KeyOf<>(new Word("b")),
        new ListOf<>("k", "l", "m"),
        new MetadataOf()
            .with("expiration", LocalDateTime.now().plusSeconds(10L))
    )
);

// After 5 seconds the first entry will be removed automatically
```

### Logging cache usage

To log cache usage, simply decorate the `Cache` with the `Logged` decorator:

```java
// Create a logger
final Logger logger = Logger.getLogger(MyClass.class.getName());

// Create a logged cache, just decorating a Cache object
final Cache<Word, List<String>> cache = new Logged<>(
    new CacheOf<>(),
    "cache",
    logger
);

// Normal cache usage
```

### Getting statistics

To collect cache statistics, decorate the `Cache` with the `Instrumented`
decorator:

```java
final Cache<Word, List<String>> cache = new Instrumented<>(
    new CacheOf<>()
);

// Normal cache usage (save, retrieve, etc.)

// Collect statistics
final Statistics stats = cache.statistics();

// Get the number of hits, for example
final int hits = stats.statistic("hits").value();
```

### Multiple composition

Remember: Cactoos-Cache is a truly object-oriented and highly composable cache.
You can combine multiple decorators, for example:

```java
// Create a instrumented, logged and policed cache
final Cache<Word, List<String>> cache = new Instrumented<>(
    new Logged<>(
        new Policed<>(
            new CacheOf<>(),
            new ExpiredPolicy<>(),
            new MaxSizePolicy<>()
        ),
        "cache",
        logger
    )
);

// Normal cache usage, with logging and statistics enabled
```

## Contributions

Contributions are welcome! Please, open an issue before submitting any kind of
contribution (ideas, documentation, code, etc.).

## How to build

```bash
mvn clean install -Pqulice
```

## License

MIT.
