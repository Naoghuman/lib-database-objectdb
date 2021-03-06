Lib-Database-ObjectDB
===

[![Build Status](https://travis-ci.org/Naoghuman/lib-database-objectdb.svg?branch=master)](https://travis-ci.org/Naoghuman/lib-database-objectdb)
[![license: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![GitHub release](https://img.shields.io/github/release/Naoghuman/lib-database-objectdb.svg)](https://GitHub.com/Naoghuman/lib-database-objectdb/releases/)




Intention
---

Lib-Database-ObjectDB is a library for easy accessing an [ObjectDB] database in 
a [JavaFX] &amp; [Maven] desktop application.

_Image:_ [UML] Lib-Database-ObjectDB v0.6.0  
![UML-diagram_Lib-Database-ObjectDB_v0.6.0_2018-09-23_15-06.png][UML-diagram_Lib-Database-ObjectDB_v0.6.0_2018-09-23_15-06]

> __Hint__  
> The `UML` diagram is created with the `Online Modeling Platform` [GenMyModel].



Content
---

* [Examples](#Examples)
    - [How to register an database](#HoToReAnDa)
    - [Create and update an `Exercise`](#CrAnUpAnEx)
    - [Sample of the entity `Exercise`](#SaOfThEnEx)
* [Api](#Api)
    - [com.github.naoghuman.lib.database.core.DatabaseFacade](#DatabaseFacade)
    - [com.github.naoghuman.lib.database.core.CrudService](#CrudService)
    - [com.github.naoghuman.lib.database.core.Database](#Database)
    - [com.github.naoghuman.lib.database.core.Converter](#Converter)
    - [com.github.naoghuman.lib.database.core.ConverterFacade](#ConverterFacade)
* [Download](#Download)
* [Requirements](#Requirements)
* [Installation](#Installation)
* [Documentation](#Documentation)
* [Contribution](#Contribution)
* [License](#License)
* [Autor](#Autor)
* [Contact](#Contact)



Examples<a name="Examples" />
---


### How to register an database<a name="HoToReAnDa" />

```java
public class StartApplication extends Application {
    ...
    @Override
    public void init() throws Exception {
        // Register the resource-bundle
        PropertiesFacade.getDefault().register(KEY__APPLICATION__RESOURCE_BUNDLE);
        ...
        // Register the database
        DatabaseFacade.getDefault().register(Properties.getPropertyForApplication(KEY__APPLICATION__DATABASE));
    }
}

public interface IPropertiesConfiguration {
    public static final String KEY__APPLICATION__RESOURCE_BUNDLE = "/com/github/naoghuman/abclist/i18n/application.properties"; // NOI18N
    public static final String KEY__TESTDATA_APPLICATION__DATABASE = "application.database"; // NOI18N
    ...
}
```


### Create and update an `Exercise`<a name="CrAnUpAnEx" />

```java
public class SqlProvider ... {
    public void createExercise(final Exercise exercise) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
                
        ExerciseSqlService.getDefault().create(exercise);
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), 1, "createExercise(Exercise)"); // NOI18N
        stopWatch.stop();
    }
    ...
    public void updateExercise(final Exercise exercise) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        ExerciseSqlService.getDefault().update(exercise);
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), 1, "updateExercise(Exercise)"); // NOI18N
        stopWatch.stop();
    }
}

final class ExerciseSqlService ... {
    void create(Exercise exercise) {
        if (Objects.equals(exercise.getId(), DEFAULT_ID)) {
            exercise.setId(System.currentTimeMillis());
            DatabaseFacade.getDefault().getCrudService().create(exercise);
        }
        else {
            this.update(exercise);
        }
    }
    ...
    void update(Exercise exercise) {
        DatabaseFacade.getDefault().getCrudService().update(exercise);
    }
}
```


### Sample of the entity `Exercise`<a name="SaOfThEnEx" />

```java
@Entity
@Access(AccessType.PROPERTY)
@Table(name = IExerciseConfiguration.ENTITY__TABLE_NAME__EXERCISE)
@NamedQueries({
    @NamedQuery(
            name = IExerciseConfiguration.NAMED_QUERY__NAME__FIND_ALL_WITH_TOPIC_ID,
            query = IExerciseConfiguration.NAMED_QUERY__QUERY__FIND_ALL_WITH_TOPIC_ID)
})
public class Exercise implements Comparable<Exercise>, Externalizable, IDefaultConfiguration, IExerciseConfiguration {
    ...

    public Exercise(long id, long topicId, long generationTime, boolean consolidated, boolean ready) {
        this.init(id, topicId, generationTime, consolidated, ready);
    }
    
    private void init(long id, long topicId, long generationTime, boolean consolidated, boolean ready) {
        this.setId(id);
        this.setTopicId(topicId);
        this.setGenerationTime(generationTime);
        this.setConsolidated(consolidated);
        this.setReady(ready);
    }
    
    // START  ID ---------------------------------------------------------------
    private LongProperty idProperty;
    private long _id = DEFAULT_ID;

    @Id
    @Column(name = EXERCISE__COLUMN_NAME__ID)
    public long getId() {
        if (idProperty == null) {
            return _id;
        } else {
            return idProperty.get();
        }
    }

    public final void setId(long id) {
        if (idProperty == null) {
            _id = id;
        } else {
            idProperty.set(id);
        }
    }

    public LongProperty idProperty() {
        if (idProperty == null) {
            idProperty = new SimpleLongProperty(this, EXERCISE__COLUMN_NAME__ID, _id);
        }
        
        return idProperty;
    }
    // END  ID -----------------------------------------------------------------

    ...

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(EXERCISE__COLUMN_NAME__ID,              this.getId())
                .append(EXERCISE__COLUMN_NAME__TOPIC_ID,        this.getTopicId())
                .append(EXERCISE__COLUMN_NAME__GENERATION_TIME, this.getGenerationTime())
                .append(EXERCISE__COLUMN_NAME__FINISHED_TIME,   this.getFinishedTime())
                .append(EXERCISE__COLUMN_NAME__CONSOLIDATED,    this.isConsolidated())
                .append(EXERCISE__COLUMN_NAME__READY,           this.isReady())
                .append(EXERCISE__COLUMN_NAME__CHOOSEN_TIME,    this.getChoosenTime())
                .toString();
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getId());
        out.writeLong(this.getTopicId());
        out.writeLong(this.getGenerationTime());
        out.writeLong(this.getFinishedTime());
        out.writeBoolean(this.isConsolidated());
        out.writeBoolean(this.isReady());
        out.writeObject(this.getChoosenTime());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId(in.readLong());
        this.setTopicId(in.readLong());
        this.setGenerationTime(in.readLong());
        this.setFinishedTime(in.readLong());
        this.setConsolidated(in.readBoolean());
        this.setReady(in.readBoolean());
        this.setChoosenTime(String.valueOf(in.readObject()));
    }
}

public interface IExerciseConfiguration {
    public static final String NAMED_QUERY__NAME__FIND_ALL_WITH_TOPIC_ID = "Exercise.findAllWithTopicId"; // NOI18N
    public static final String NAMED_QUERY__QUERY__FIND_ALL_WITH_TOPIC_ID = "SELECT e FROM Exercise e WHERE e.topicId == :topicId"; // NOI18N
    ...
}
```



Api<a name="Api" />
----


### com.github.naoghuman.lib.database.core.DatabaseFacade<a name="DatabaseFacade" />

```java
/**
 * The facade {@link com.github.naoghuman.lib.database.core.DatabaseFacade} 
 * provides access to the default implementation from the {@code Interface} 
 * {@link com.github.naoghuman.lib.database.core.Database} which is 
 * {@link com.github.naoghuman.lib.database.internal.DefaultDatabase}.
 *
 * @author Naoghuman
 * @see com.github.naoghuman.lib.database.core.Database
 * @see com.github.naoghuman.lib.database.internal.DefaultDatabase
 */
public final class DatabaseFacade implements Database
```

```java
/**
 * Returns a singleton instance from the class {@code DatabaseFacade}.
 * 
 * @return a singleton instance from the class {@code DatabaseFacade}.
 */
public static final DatabaseFacade getDefault();
```


### com.github.naoghuman.lib.database.core.Database<a name="Database" />

```java
/**
 * The {@code Interface} for the {@code Implementation} {@code Class} 
 * {@link com.github.naoghuman.lib.database.internal.DefaultDatabase}.<br>
 * Over the facade {@link com.github.naoghuman.lib.database.core.DatabaseFacade} 
 * the developer can access to the default {@code Implementation} methods from 
 * this {@code Interface}.
 *
 * @author Naoghuman
 * @see    com.github.naoghuman.lib.database.core.DatabaseFacade
 * @see    com.github.naoghuman.lib.database.internal.DefaultDatabase
 */
public interface Database
```

```java
/**
 * Allowed the developer to drop the defined database.<br>
 * This method can be used for testing purpose.
 * 
 * @param database The database which should be dropped.
 */
public void drop(final String database);
```

```java
/**
 * Returns a {@link com.github.naoghuman.lib.database.core.CrudService} with 
 * the name {@code DEFAULT} which allowed all sql operations.
 * 
 * @return The {@code CrudService}.
 * @see    com.github.naoghuman.lib.database.core.CrudService
 */
public CrudService getCrudService();
```

```java
/**
 * Returns a named {@link com.github.naoghuman.lib.database.core.CrudService} 
 * which allowed all sql operations.
 * 
 * @param  name The name from the {@code CrudService}.
 * @return The {@code CrudService}.
 * @see    com.github.naoghuman.lib.database.core.CrudService
 */
public CrudService getCrudService(final String name);
```

```java
/**
 * Returns a named {@link javax.persistence.EntityManager} which allowed 
 * all sql operations.
 * 
 * @param  name The name from the {@code EntityManager}.
 * @return The {@code EntityManager}.
 * @see    javax.persistence.EntityManager
 */
public EntityManager getEntityManager(final String name);
```

```java
/**
 * Creates a database with the specific parameter in the folder
 * {@code System.getProperty("user.dir") + File.separator + "database"} 
 * if it not exists.<br>
 * If the parameter have no suffix {@code .odb} then the suffix will be
 * automatically added, otherwise not.
 * 
 * @param database The name for the database which should be created.
 */
public void register(final String database);
```

```java
/**
 * Removes a {@link com.github.naoghuman.lib.database.core.CrudService} with 
 * the given name. Also the associated {@link javax.persistence.EntityManager} 
 * will be removed.
 * 
 * @param name The name for the {@code CrudService} which should be removed.
 * @see   com.github.naoghuman.lib.database.core.CrudService
 * @see   com.github.naoghuman.lib.database.core.Database#removeEntityManager(java.lang.String)
 * @see   javax.persistence.EntityManager
 */
public void removeCrudService(final String name);
```

```java
/**
 * Removes a {@link javax.persistence.EntityManager} with the given name. Also
 * the associated {@link com.github.naoghuman.lib.database.core.CrudService} 
 * will be removed.
 * 
 * @param name The name for the {@code EntityManager} which should be removed.
 * @see com.github.naoghuman.lib.database.core.Database#removeCrudService(java.lang.String)
 */
public void removeEntityManager(final String name);
```

```java
/**
 * Close the previous registered database.
 * 
 * @see com.github.naoghuman.lib.database.core.Database#register(java.lang.String)
 */
public void shutdown();
```


### com.github.naoghuman.lib.database.core.CrudService<a name="CrudService" />

```java
/**
 * The {@code Interface} for the default {@code Implementation} class 
 * {@link com.github.naoghuman.lib.database.internal.DefaultCrudService}.<br>
 * A common {@code Interface} for all CRUD operations. The type of the entity is 
 * specified in the {@code Implementation}.
 *
 * @author Naoghuman
 * @see    com.github.naoghuman.lib.database.core.DatabaseFacade
 * @see    com.github.naoghuman.lib.database.internal.DefaultCrudService
 */
public interface CrudService
```

```java
/**
 * Start a resource transaction.
 * <p>
 * Internal following methods in following order will be executed:<br>
 * <ul>
 * <li>{@link javax.persistence.EntityTransaction#begin()}</li>
 * </ul>
 * 
 * @see javax.persistence.EntityTransaction#begin()
 */
public void beginTransaction();
```

```java
/**
 * Commits the current resource transaction, writing any unflushed changes 
 * to the database.
 * <p>
 * Internal following methods in following order will be executed:<br>
 * <ul>
 * <li>{@link javax.persistence.EntityTransaction#commit()}</li>
 * <li>{@link javax.persistence.EntityManager#clear()}</li>
 * </ul>
 * 
 * @see javax.persistence.EntityManager#clear()
 * @see javax.persistence.EntityTransaction#commit()
 */
public void commitTransaction();
```

```java
/**
 * Counts all entitys in the given {@code table}.<br>
 * Creates the {@code sql} instruction <i>SELECT COUNT(c) FROM table c</i> which
 * will then executed with {@link javax.persistence.EntityManager#createQuery(java.lang.String, java.lang.Class)}.
 * 
 * @param  table The table which entitys should be counted.
 * @return The number of entitys in the table or {@code -1} if the table doesn't exists.
 * @see    javax.persistence.EntityManager#createQuery(java.lang.String, java.lang.Class)
 */
public Long count(final String table);
```

```java
/**
 * Makes an entity managed and persistent. Synchronize the persistence context 
 * to the underlying database and refresh the state of the instance from the 
 * database, overwriting changes made to the entity, if any.<br>
 * Delegates to {@link com.github.naoghuman.lib.database.core.CrudService#create(java.lang.Object, java.lang.Boolean)}
 * with the parameter {@code isSingleTransaction == true}.
 * 
 * @param  <T> the type of the entity
 * @param  entity entity instance
 * @return a created persisted instance which the given type
 * @see    com.github.naoghuman.lib.database.core.CrudService#create(java.lang.Object, java.lang.Boolean)
 */
public <T> T create(final T entity);
```

```java
/**
 * Makes an entity managed and persistent. Synchronize the persistence context 
 * to the underlying database and refresh the state of the instance from the 
 * database, overwriting changes made to the entity, if any. 
 * <p>
 * Internal following methods in following order will be executed:<br>
 * <ul>
 * <li>if {@code isSingleTransaction == true} then {@link com.github.naoghuman.lib.database.core.CrudService#beginTransaction()}</li>
 * <li>{@link javax.persistence.EntityManager#persist(java.lang.Object)}</li>
 * <li>{@link javax.persistence.EntityManager#flush() }</li>
 * <li>{@link javax.persistence.EntityManager#refresh(java.lang.Object)}</li>
 * <li>if {@code isSingleTransaction == true} then {@link com.github.naoghuman.lib.database.core.CrudService#commitTransaction()}</li>
 * </ul>
 * 
 * @param  <T>    the type of the entity
 * @param  entity entity instance
 * @param  isSingleTransaction flag if is transaction a single transaction or not.
 * @return a created persisted instance which the given type
 * @see    com.github.naoghuman.lib.database.core.CrudService#beginTransaction()
 * @see    com.github.naoghuman.lib.database.core.CrudService#commitTransaction()
 * @see    com.github.naoghuman.lib.database.core.CrudService#create(java.lang.Object)
 * @see    javax.persistence.EntityManager#flush()
 * @see    javax.persistence.EntityManager#persist(java.lang.Object)
 * @see    javax.persistence.EntityManager#refresh(java.lang.Object)
 */
public <T> T create(final T entity, final Boolean isSingleTransaction);
```

```java
/**
 * Remove the entity instance from the database.<br>
 * Deletes to {@link com.github.naoghuman.lib.database.core.CrudService#delete(java.lang.Class, java.lang.Object, java.lang.Boolean)}
 * with the parameter {@code isSingleTransaction == true}.
 * 
 * @param <T> the type of the entity
 * @param type the entity class
 * @param id the primary key
 * @see   com.github.naoghuman.lib.database.core.CrudService#delete(java.lang.Class, java.lang.Object, java.lang.Boolean) 
 */
public <T> void delete(final Class<T> type, final Object id);
```

```java
/**
 * Remove the entity instance from the database.<br>
 * <p>
 * Internal following methods in following order will be executed:<br>
 * <ul>
 * <li>if {@code isSingleTransaction == true} then {@link com.github.naoghuman.lib.database.core.CrudService#beginTransaction()}</li>
 * <li>{@link javax.persistence.EntityManager#getReference(java.lang.Class, java.lang.Object)}</li>
 * <li>{@link javax.persistence.EntityManager#remove(java.lang.Object)}</li>
 * <li>if {@code isSingleTransaction == true} then {@link com.github.naoghuman.lib.database.core.CrudService#commitTransaction()}</li>
 * </ul>
 * 
 * @param <T> the type of the entity
 * @param type the entity class
 * @param id the primary key
 * @param isSingleTransaction flag if is transaction a single transaction or not.
 * @see   com.github.naoghuman.lib.database.core.CrudService#delete(java.lang.Class, java.lang.Object)
 * @see   javax.persistence.EntityManager#getReference(java.lang.Class, java.lang.Object)
 * @see   javax.persistence.EntityManager#remove(java.lang.Object)
 */
public <T> void delete(final Class<T> type, final Object id, final Boolean isSingleTransaction);
```

```java
/**
 * Returns the associated {@link javax.persistence.EntityManager}.
 * 
 * @return The EntityManager.
 */
public EntityManager getEntityManager();
```

```java
/**
 * TODO Add JavaDoc<br>
 * Delegates to {@link com.github.naoghuman.lib.database.core.CrudService#update(java.lang.Object, java.lang.Boolean) }
 * with the parameter {@code isSingleTransaction == true}.
 * 
 * @param  <T> the type of the entity
 * @param  entity entity instance
 * @return 
 * @see    com.github.naoghuman.lib.database.core.CrudService#update(java.lang.Object, java.lang.Boolean) 
 */
public <T> T update(final T entity);
```

```java
/**
 * Merge the state of the given entity into the current persistence context.
 * 
 * @param  <T> the type of the entity
 * @param  entity entity instance
 * @param  isSingleTransaction flag if is transaction a single transaction or not.
 * @return 
 * @see    com.github.naoghuman.lib.database.core.CrudService#update(java.lang.Object) 
 */
public <T> T update(final T entity, final Boolean isSingleTransaction);
```

```java
/**
 * Find by primary key. Search for an entity of the specified class and 
 * primary key. If the entity instance is contained in the persistence 
 * context, it is returned from there.
 * 
 * @param  <T> the type of the entity
 * @param  type the entity class
 * @param  id the primary key
 * @return 
 */
public <T> T findById(final Class<T> type, final Object id);
```

```java
/**
 * TODO Add JavaDoc
 * 
 * @param  <T> the type of the entity
 * @param  type the entity class
 * @param  queryName
 * @return 
 */
public <T> List<T> findByNamedQuery(final Class<T> type, final String queryName);
```

```java
/**
 * TODO Add JavaDoc
 * 
 * @param  <T> the type of the entity
 * @param  type the entity class
 * @param  queryName
 * @param  resultLimit
 * @return 
 */
public <T> List<T> findByNamedQuery(final Class<T> type, final String queryName, final int resultLimit);
```

```java
/**
 * TODO Add JavaDoc
 * 
 * @param  <T> the type of the entity
 * @param  type the entity class
 * @param  queryName
 * @param  parameters
 * @return 
 */
public <T> List<T> findByNamedQuery(final Class<T> type, final String queryName, final Map<String, Object> parameters);
```

```java
/**
 * TODO Add JavaDoc
 * 
 * @param  <T> the type of the entity
 * @param  type the entity class
 * @param  queryName
 * @param  parameters
 * @param  resultLimit
 * @return 
 */
public <T> List<T> findByNamedQuery(final Class<T> type, final String queryName, final Map<String, Object> parameters, final int resultLimit);
```

```java
/**
 * TODO Add JavaDoc
 * 
 * @param  <T> the type of the entity
 * @param  type the entity class
 * @param  sql
 * @return 
 */
public <T> List<T> findByNativeQuery(final Class<T> type, final String sql);
```

```java
/**
 * TODO Add JavaDoc
 * 
 * @param  <T> the type of the entity
 * @param  type the entity class
 * @param  sql
 * @param  resultLimit
 * @return 
 */
public <T> List<T> findByNativeQuery(final Class<T> type, final String sql, final int resultLimit);
```

```java
/**
 * TODO Add JavaDoc
 * 
 * @param  <T> the type of the entity
 * @param  type the entity class
 * @param  sql
 * @param  parameters
 * @return 
 */
public <T> List<T> findByNativeQuery(final Class<T> type, final String sql, final Map<String, Object> parameters);
```

```java
/**
 * TODO Add JavaDoc
 * 
 * @param  <T> the type of the entity
 * @param  type the entity class
 * @param  sql
 * @param  parameters
 * @param  resultLimit
 * @return 
 */
public <T> List<T> findByNativeQuery(final Class<T> type, final String sql, final Map<String, Object> parameters, final int resultLimit);
```

```java
/**
 * TODO Add JavaDoc
 * 
 * @param name 
 */
public void shutdown(final String name);
```


### com.github.naoghuman.lib.database.core.Converter<a name="Converter" />

```java
/**
 * This {@code Interface} allowed to convert objects to a {@link java.lang.String} 
 * and returned to the given type.
 * <p>
 * For example with the facade {@link com.github.naoghuman.lib.database.core.ConverterFacade} 
 * the developer have momentary the possiblility to convert a {@code JavaFX} color 
 * or a {@link java.time.LocalDateTime}.
 * 
 * @param  <T> the type of class which should be convert.
 * @author Naoghuman
 * @since  0.6.0
 * @see    com.github.naoghuman.lib.database.core.ConverterFacade
 * @see    java.lang.String
 * @see    java.time.LocalDateTime
 * @see    javafx.scene.paint.Color
 */
public interface Converter<T>
```

```java
/**
 * Definition from a constant for the used delimiter ({@code ;}) in context 
 * from this {@code Interface}.
 * 
 * @since  0.6.0
 * @author Naoghuman
 */
public static final String DELIMITER = ";"; // NOI18N
```

```java
/**
 * Converts the internal values (separated with the delimiter {@code ';'}) 
 * from the given object to a {@link java.lang.String}.
 * <br>
 * To separate the internal values the developer can use the constant 
 * {@link com.github.naoghuman.lib.database.core.Converter#DELIMITER}.
 * <p>
 * For example converting the JavaFX color {@link javafx.scene.paint.Color#BLACK} with 
 * {@link com.github.naoghuman.lib.database.core.ConverterFacade#getColorConverter()} 
 * will returns the String {@code 0.0;0.0;0.0;1.0}.
 * 
 * @param  value the object which internal values should be convert to a {@code String}.
 * @return a {@code String} which presented the internal values from the given object.
 * @throws java.lang.NullPointerException if value is {@code NULL}.
 * @since  0.6.0
 * @author Naoghuman
 * @see    com.github.naoghuman.lib.database.core.Converter#DELIMITER
 * @see    com.github.naoghuman.lib.database.core.ConverterFacade#getColorConverter()
 * @see    java.lang.String
 * @see    javafx.scene.paint.Color#BLACK
 */
public String to(final T value);
```

```java
/**
 * Converts the {@code value} to the given class. If the {@link java.lang.String} 
 * can't converted to the given type then {@link java.util.Optional#empty()} 
 * will returned.
 * <p>
 * The string should be a {@code separated value list} separated with the 
 * delimiter {@code ';'}. See also 
 * {@link com.github.naoghuman.lib.database.core.Converter#DELIMITER}.
 * 
 * @param  value the object which should be converted returned to the given type.
 * @return an instance from the given class or {@link java.util.Optional#empty()}.
 * @throws java.lang.IllegalArgumentException if value is {@code EMPTY}.
 * @throws java.lang.NullPointerException if value is {@code NULL}.
 * @since  0.6.0
 * @author Naoghuman
 * @see    com.github.naoghuman.lib.database.core.Converter#DELIMITER
 * @see    java.lang.String
 * @see    java.util.Optional
 */
public Optional<T> from(final String value);
```


### com.github.naoghuman.lib.database.core.ConverterFacade<a name="ConverterFacade" />

```java
/**
 * The facade {@code ConverterFacade} allowed access to existing default implementations 
 * from the {@code Interface} {@link com.github.naoghuman.lib.database.core.Converter}.
 * <p>
 * Momentary following default implementations exists:
 * <ul>
 * <li>{@link com.github.naoghuman.lib.database.internal.DefaultColorConverter}</li>
 * <li>{@link com.github.naoghuman.lib.database.internal.DefaultLocalDateTimeConverter}</li>
 * </ul>
 *
 * @since  0.6.0
 * @author Naoghuman
 * @see    com.github.naoghuman.lib.database.core.Converter
 * @see    com.github.naoghuman.lib.database.internal.DefaultColorConverter
 * @see    com.github.naoghuman.lib.database.internal.DefaultLocalDateTimeConverter
 */
public final class ConverterFacade
```

```java
/**
 * Returns a singleton instance from the class {@link com.github.naoghuman.lib.database.core.ConverterFacade}.
 * 
 * @return a singleton instance from this class.
 * @since  0.6.0
 * @author Naoghuman
 */
public static final ConverterFacade getDefault()
```



Download<a name="Download" />
---

Current `version` is `0.6.0`. Main points in this release are:
* This is a mayor update.
* Bind the github project to travis.com for stability.
* Add badges from img.shield.io to the ReadMe for additional informations.
* Add new interface `Converter` to the api. Alloweds to convert objects to 
  Strings and back again.

**Maven coordinates**  
```xml
<dependencies>
    <dependency>
        <groupId>com.github.naoghuman</groupId>
        <artifactId>lib-database-objectdb</artifactId>
        <version>0.6.0</version>
    </dependency>
    <dependency>
        <groupId>com.github.naoghuman</groupId>
        <artifactId>lib-logger</artifactId>
        <version>0.6.0</version>
    </dependency>
</dependencies>
```

Download:
* [Release v0.6.0 (09.24.2017 / MM.dd.yyyy)]

An overview about all existings releases can be found here:
* [Overview from all releases in Lib-Database-ObjectDB]



Requirements<a name="Requirements" />
---

* On your system you need [JRE 8] or [JDK 8] installed.
* The library [lib-database-objectdb-0.6.0.jar](#Installation).
  * Included is the [objectdb-2.7.6.jar].
  * Included is the [javax.persistence-2.2.1.jar].
  * Included is the [javax.transaction-1.1.jar].

In the library are following libraries registered as dependencies:
* The library [lib-logger-0.6.0.jar](#Installation).
  * Included in `Lib-Logger` is the library [log4j-api-2.10.0.jar].
  * Included is `Lib-Logger` is the library [log4j-core-2.10.0.jar].


Installation<a name="Installation" />
---

* If not installed download the [JRE 8] or the [JDK 8].
  * Optional: To work better with [FXML] files in a [JavaFX] application 
    download the [JavaFX Scene Builder] under 'Additional Resources'.
* Choose your preferred IDE (e.g. [NetBeans], [Eclipse] or [IntelliJ IDEA]) for 
  development.
* Download or clone [Lib-Database-ObjectDB].
* Download or clone [Lib-Logger].
* Open the projects in your IDE and run them.


Documentation<a name="Documentation" />
---

* In section [Api](#Api) you can see the main point(s) to access the functionality 
  in this library.
* For additional information see the [JavaDoc] in the library itself.



Contribution<a name="Contribution" />
---

* If you find a `Bug` I will be glad if you will report an [Issue].
* If you want to contribute to the project plz fork the project and do a [Pull Request].



License<a name="License" />
---

The project `Lib-Database-ObjectDB` is licensed under [General Public License 3.0].



Autor<a name="Autor" />
---

The project `Lib-Database-ObjectDB` is maintained by me, Peter Rogge. See [Contact](#Contact).



Contact<a name="Contact" />
---

You can reach me under <peter.rogge@yahoo.de>.



[//]: # (Images)
[UML-diagram_Lib-Database-ObjectDB_v0.6.0_2018-09-23_15-06]:https://user-images.githubusercontent.com/8161815/45928357-88e11800-bf42-11e8-8e17-1c391f677ca4.png



[//]: # (Links)
[Eclipse]:https://www.eclipse.org/
[FXML]:http://docs.oracle.com/javafx/2/fxml_get_started/jfxpub-fxml_get_started.htm
[General Public License 3.0]:http://www.gnu.org/licenses/gpl-3.0.en.html
[GenMyModel]:https://www.genmymodel.com/
[IntelliJ IDEA]:http://www.jetbrains.com/idea/
[Issue]:https://github.com/Naoghuman/lib-database-objectdb/issues
[JavaDoc]:http://www.oracle.com/technetwork/java/javase/documentation/index-jsp-135444.html
[JavaFX]:http://docs.oracle.com/javase/8/javase-clienttechnologies.htm
[JavaFX Scene Builder]:http://gluonhq.com/labs/scene-builder/
[javax.persistence-2.2.1.jar]:http://search.maven.org/#artifactdetails|org.eclipse.persistence|javax.persistence|2.2.1|jar
[javax.transaction-1.1.jar]:http://mvnrepository.com/artifact/javax.transaction/jta
[JDK 8]:http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[JRE 8]:http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
[Lib-Database-ObjectDB]:https://github.com/Naoghuman/lib-database-objectdb
[Lib-Logger]:https://github.com/Naoghuman/lib-logger
[log4j-api-2.10.0.jar]:https://logging.apache.org/log4j/2.0/log4j-web/dependencies.html
[log4j-core-2.10.0.jar]:https://logging.apache.org/log4j/2.0/log4j-web/dependencies.html
[Maven]:http://maven.apache.org/
[NetBeans]:https://netbeans.org/
[ObjectDB]:http://www.objectdb.com/
[objectdb-2.7.6.jar]:http://www.objectdb.com/object/db/database/download
[Overview from all releases in Lib-Database-ObjectDB]:https://github.com/Naoghuman/lib-database-objectdb/releases
[Pull Request]:https://help.github.com/articles/using-pull-requests
[Release v0.6.0 (09.24.2017 / MM.dd.yyyy)]:https://github.com/Naoghuman/lib-database-objectdb/releases/tag/v0.6.0
[UML]:https://en.wikipedia.org/wiki/Unified_Modeling_Language


