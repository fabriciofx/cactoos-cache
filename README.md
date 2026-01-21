# Cactoos-Cache

## Introduction

Cactoos-Cache is a truly object-oriented implementation of caching.

**Motivation**.
We are not happy with many existing caching APIs because they are procedural
rather than object-oriented. They do their job, but mostly through strongly
coupled classes and static methods. Cactoos-Cache proposes doing almost exactly
the same thing, but through (more OO) objects.

**Principles**.
These are the [design principles](http://www.elegantobjects.org#principles)
behind Cactoos-Cache.

## How to use

```xml
<dependency>
  <groupId>com.github.fabriciofx</groupId>
  <artifactId>cactoos-cache</artifactId>
  <version>0.0.2</version>
</dependency>
```

Required Java version: 17+.

## Terminology

A `Key` is composed of a domain and a hash. An `Entry` is the triple consisting
of a `Key`, a `Value` (which you want to associate with the `Key`) and
`Metadata`. `Metadata` can be anything (as `String`s), as SQL tables names
(which are used for cache invalidation). A `Store` is responsible for
maintaining the association `Key` -> `Entry`. Using a `Store`, you can:

- Retrieve an `Entry` using a `Key`;
- Associate (save) a `Key` with an `Entry`;
- Delete an association by `Key`;
- Check whether an association `Key` -> `Entry` exists;
- Retrieve all `Key`s;
- Retrieve all `Entry`s.

A `Cache` is responsible for providing a `Store` and generating `Statistics`
about `Store` usage. The most common statistics are:

- *Hits*: number of times a value was found in the cache;
- *Misses*: number of times a value was not found in the cache;
- *Lookups*: total number of accesses (hits + misses);
- *Invalidations*: explicit cache removals;
- *Evictions*: automatic cache removals.

These statistics are already provided, but you can create your own if needed.

## Usage

Suppose do you want to create a cache for a set of words, where each word is
associated with a list of synonyms. For that, you need to create a `Key`, an
`Entry`, a `Store` and a `Cache`.

- Creating a `Key`

You can create a class that implements `Key` where the *domain* is the value you
want to associate (a word):

```java
public final class WordsKey implements Key<String> {
    private final String word;

    public WordsKey(final String word) {
      this.word = word;
    }

    @Override
    public String domain() {
        return this.word;
    }

    @Override
    public String hash() {
        return new UncheckedText(
            new HexOf(
                new Murmur3Hash(
                    new BytesOf(
                        new TextOf(this.word)
                    )
                )
            )
        ).asString()
    }
}
```

Or simply:

```java
public final class WordsKey extends KeyEnvelope<String> {
    // Constructors that call super() if necessary
}
```

- Creating an `Entry`:

```java
public final class WordsEntry implements Entry<List<String>> {
    private final Key<String> key;
    private final List<String> value;
    private final Map<String, List<String>> metadata;

    public WordsEntry(final Key<String> key, final List<String> value) {
        this(key, value, new HashMap<>());
    }

    public WordsEntry(
        final Key<String> key,
        final List<String> value,
        final Map<String, List<String>>> metadata
    ) {
        this.key = key;
        this.value = value;
        this.metadata = metadata;
    }

    @Override
    public Key<String> key() {
        return this.key;
    }

    @Override
    public List<String> value() {
        return this.value;
    }

    @Override
    public Map<String, List<String>> metadata() {
        return this.metadata;
    }

    @Override
    public boolean valid() {
        return true;
    }
}
```

Or simply:

```java
public final class WordsEntry extends EntryEnvelope<List<String>> {
    // Constructors that call super() if necessary
}
```

- Creating a `Store`

Follow the same process:

```java
public final class WordsStore implements StoreEnvelope<String, List<String>> {
    // Constructors that call super() if necessary
}
```

Or you can implement the `Store` interface directly.

- Creating a `Cache`

```java
public final class WordsCache implements CacheEnvelope<String, List<String>> {
    public WordsCache() {
        super(new WordsStore());
    }
}
```

Or you can implement the `Cache` interface directly.

- Storing and retrieving from the cache

```java
final Cache<String, List<String>> cache = new WordsCache();
cache.store().save(
    new WordsKey("a"),
    new WordsEntry(
        new WordsKey("a"),
        new ListOf<>("x", "y", "z")
    )
);
cache.store().save(
    new WordsKey("b"),
    new WordsEntry(
        new WordsKey("b"),
        new ListOf<>("k", "l", "m")
    )
);
final List<String> words = cache
                            .store()
                            .retrieve(new WordsKey("a"))
                            .value();
```

- Deleting from the cache

```java
final Cache<String, List<String>> cache = new WordsCache();
// Store some values
cache.store().delete(new WordsKey("a"));
```

- Logging cache usage

To log cache usage, simply decorate the `Cache` with the `Logged` decorator:

```java
final Cache<String, List<String>> cache = new Logged<>(
    new WordsCache(),
    "cache",
    logger
);
// Normal cache usage
```

- Getting statistics

To collect cache statistics, decorate the `Cache` with the `Instrumented`
decorator:

```java
final Cache<String, List<String>> cache = new Instrumented<>(
    new WordsCache()
);
// Normal cache usage
final Statistics stats = cache.statistics();

// Get the number of hits
final int hits = stats.statistic("hits").value();
```

- Multiple composition

Remember: Cactoos-Cache is a truly object-oriented and highly composable cache.
You can combine multiple decorators, for example:

```java
final Cache<String, List<String>> cache = new Instrumented<>(
    new Logged<>(
        new WordsCache(),
        "cache",
        logger
    )
);
// Normal cache usage, with logging and statistics enabled
```

## Contributions

Contributions are welcome! Please, open an issue before submitting any kind of
contribution (ideas, documentation, code, etc.).

### How to build

```bash
mvn clean install -Pqulice
```

# License

MIT.
