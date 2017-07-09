Lib-Database-ObjectDB
===



Intention
---

Lib-Database-ObjectDB is a library for easy accessing an [ObjectDB] database in 
a [JavaFX] &amp; [Maven] desktop application.

_Image:_ [UML] Lib-Database-ObjectDB 
![UML-diagram_Lib-Database-ObjectDB_v0.5.0_2017-07-09_19-21.png][UML-diagram_Lib-Database-ObjectDB_v0.5.0_2017-07-09_19-21]

> __Hint__  
> The `UML` diagram is created with the `Online Modeling Platform` [GenMyModel].


Current `version` is `0.4.1` (05.22.2017).



Content
---

* [Examples](#Examples)
    - [How to register an database](#registerAnDB)
    - [Create and update an `Exercise`](#createAndUpdateAnEx)
    - [Sample of the entity `Exercise`](#sampleOfAnEntity)
* [Api](#Api)
    - [com.github.naoghuman.lib.database.api.ICrudService](#ICrudService)
    - [com.github.naoghuman.lib.database.api.DatabaseFacade](#DatabaseFacade)
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


### How to register an database<a name="registerAnDB" />

```java
public class StartApplication ... {
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


### Create and update an `Exercise`<a name="createAndUpdateAnEx" />

```java
public class SqlProvider ... {
    public void createExercise(final Exercise exercise) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
                
        ExerciseSqlService.getDefault().create(exercise);
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), 1, "createExercise(Exercise exercise)"); // NOI18N
        stopWatch.stop();
    }
    ...
    public void updateExercise(final Exercise exercise) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        ExerciseSqlService.getDefault().update(exercise);
        
        stopWatch.split();
        this.printToLog(stopWatch.toSplitString(), 1, "updateExercise(Exercise exercise)"); // NOI18N
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


### Sample of the entity `Exercise`<a name="sampleOfTheEntityEx" />

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

    ...

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(EXERCISE__COLUMN_NAME__ID, this.getId())
                .append(EXERCISE__COLUMN_NAME__TOPIC_ID, this.getTopicId())
                .append(EXERCISE__COLUMN_NAME__GENERATION_TIME, this.getGenerationTime())
                .append(EXERCISE__COLUMN_NAME__FINISHED_TIME, this.getFinishedTime())
                .append(EXERCISE__COLUMN_NAME__CONSOLIDATED, this.isConsolidated())
                .append(EXERCISE__COLUMN_NAME__READY, this.isReady())
                .append(EXERCISE__COLUMN_NAME__CHOOSEN_TIME, this.getChoosenTime())
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

### com.github.naoghuman.lib.database.api.ICrudService<a name="ICrudService" />

```java
/**
 * The <code>Interface</code> for the class {@link com.github.naoghuman.lib.database.CrudService}.<br />
 * A common <code>Interface</code> for all CRUD-Component implementations. The
 * type of the entity is specified in the implementation.
 *
 * @author PRo
 * @see com.github.naoghuman.lib.database.CrudService
 * @see com.github.naoghuman.lib.database.api.DatabaseFacade
 */
public interface ICrudService
```


```java
/**
 * Start a resource transaction.
 * <p>
 * Internal following methods will be executed in following order:<br>
 * <ul>
 * <li>{@link javax.persistence.EntityTransaction#begin()}</li>
 * </ul>
 */
public void beginTransaction();
```


```java
/**
 * Commit the current resource transaction, writing any unflushed changes 
 * to the database.
 * <p>
 * Internal following methods will be executed in following order:<br>
 * <ul>
 * <li>{@link javax.persistence.EntityTransaction#commit()}</li>
 * <li>{@link javax.persistence.EntityManager#clear()}</li>
 * </ul>
 */
public void commitTransaction();
```


```java
/**
 * Count all entitys in the given table.
 * 
 * @param table The table which entitys should be counted.
 * @return The number of entitys in the table or -1 if the table doesn't exists.
 */
public Long count(String table);
```


```java
/**
 * Make an entity managed and persistent.<br>
 * Deletes to {@link ICrudService#create(java.lang.Object, java.lang.Boolean)}
 * with the parameter <code>isSingleTransaction = true</code>.
 * 
 * @param <T>
 * @param entity
 * @return 
 * @see ICrudService#create(java.lang.Object, java.lang.Boolean)
 */
public <T> T create(T entity);
```


```java
/**
 * Make an entity managed and persistent.
 * <p>
 * Internal following methods will be executed in following order:<br>
 * <ul>
 * <li>if <code>isSingleTransaction == true</code> then {@link com.github.naoghuman.lib.database.CrudService#beginTransaction()}</li>
 * <li>{@link javax.persistence.EntityManager#persist(java.lang.Object)}</li>
 * <li>{@link javax.persistence.EntityManager#flush() }</li>
 * <li>{@link javax.persistence.EntityManager#refresh(java.lang.Object) }</li>
 * <li>if <code>isSingleTransaction == true</code> then {@link com.github.naoghuman.lib.database.CrudService#commitTransaction()}</li>
 * </ul>
 * 
 * @param <T>
 * @param entity
 * @param isSingleTransaction
 * @return 
 * @see ICrudService#create(java.lang.Object) 
 */
public <T> T create(T entity, Boolean isSingleTransaction);
```


```java
/**
 * TODO Add JavaDoc<br>
 * Deletes to {@link ICrudService#delete(java.lang.Class, java.lang.Object, java.lang.Boolean)}
 * with the parameter <code>isSingleTransaction = true</code>.
 * 
 * @param <T>
 * @param type
 * @param id 
 * @see ICrudService#delete(java.lang.Class, java.lang.Object, java.lang.Boolean) 
 */
public <T> void delete(Class<T> type, Object id);
```


```java
/**
 * TODO Add JavaDoc
 * 
 * @param <T>
 * @param type
 * @param id
 * @param isSingleTransaction 
 * @see ICrudService#delete(java.lang.Class, java.lang.Object) 
 */
public <T> void delete(Class<T> type, Object id, Boolean isSingleTransaction);
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
 * Delegates to {@link ICrudService#update(java.lang.Object, java.lang.Boolean) }
 * with the parameter <code>isSingleTransaction = true</code>.
 * 
 * @param <T>
 * @param entity
 * @return 
 * @see ICrudService#update(java.lang.Object, java.lang.Boolean) 
 */
public <T> T update(T entity);
```


```java
/**
 * Merge the state of the given entity into the current persistence context.
 * 
 * @param <T>
 * @param entity
 * @param isSingleTransaction
 * @return 
 * @see ICrudService#update(java.lang.Object) 
 */
public <T> T update(T entity, Boolean isSingleTransaction);
```


```java
/**
 * Find by primary key. Search for an entity of the specified class and 
 * primary key. If the entity instance is contained in the persistence 
 * context, it is returned from there.
 * 
 * @param <T>
 * @param type
 * @param id
 * @return 
 */
public <T> T findById(Class<T> type, Object id);
```


```java
/**
 * TODO Add JavaDoc
 * 
 * @param <T>
 * @param type
 * @param queryName
 * @return 
 */
public <T> List<T> findByNamedQuery(Class<T> type, String queryName);
```


```java
/**
 * TODO Add JavaDoc
 * 
 * @param <T>
 * @param type
 * @param queryName
 * @param parameters
 * @return 
 */
public <T> List<T> findByNamedQuery(Class<T> type, String queryName, Map<String, Object> parameters);
```


```java
/**
 * TODO Add JavaDoc
 * 
 * @param <T>
 * @param type
 * @param queryName
 * @param parameters
 * @param resultLimit
 * @return 
 */
public <T> List<T> findByNamedQuery(Class<T> type, String queryName, Map<String, Object> parameters, int resultLimit);
```


```java
/**
 * TODO Add JavaDoc
 * 
 * @param <T>
 * @param type
 * @param sql
 * @return 
 */
public <T> List<T> findByNativeQuery(Class<T> type, String sql);
```


```java
/**
 * TODO Add JavaDoc
 * 
 * @param <T>
 * @param type
 * @param sql
 * @param resultLimit
 * @return 
 */
public <T> List<T> findByNativeQuery(Class<T> type, String sql, int resultLimit);
```


```java
/**
 * TODO Add JavaDoc
 * 
 * @param <T>
 * @param type
 * @param sql
 * @param parameters
 * @return 
 */
public <T> List<T> findByNativeQuery(Class<T> type, String sql, Map<String, Object> parameters);
```


```java
/**
 * TODO Add JavaDoc
 * 
 * @param <T>
 * @param type
 * @param sql
 * @param parameters
 * @param resultLimit
 * @return 
 */
public <T> List<T> findByNativeQuery(Class<T> type, String sql, Map<String, Object> parameters, int resultLimit);
```


```java
/**
 * TODO Add JavaDoc
 * 
 * @param name 
 */
public void shutdown(String name);
```


### com.github.naoghuman.lib.database.api.DatabaseFacade<a name="DatabaseFacade" />

```java
/**
 * The facade {@link com.github.naoghuman.lib.database.api.DatabaseFacade} provides 
 * access to the action methods during the Interface {@link com.github.naoghuman.lib.database.api.ILibDatabase}.
 *
 * @author PRo
 * @see com.github.naoghuman.lib.database.api.ILibDatabase
 */
public final class DatabaseFacade implements ILibDatabase {
```


```java
/**
 * Allowed the developer to drop the defined database.<br />
 * This method can be used for testing purpose.
 * 
 * @param database The database which should be dropped.
 */
public void drop(String database);
```


```java
/**
 * Returns a {@link com.github.naoghuman.lib.database.api.ICrudService} with the 
 * name DEFAULT which allowed all sql operations.
 * 
 * @return The crud service.
 */
public ICrudService getCrudService();
```


```java
/**
 * Returns a named {@link com.github.naoghuman.lib.database.api.ICrudService} 
 * which allowed all sql operations.
 * 
 * @param name The name from the <code>ICrudService</code>.
 * @return The <code>ICrudService</code>.
 */
public ICrudService getCrudService(String name);
```


```java
/**
 * Returns a named {@link javax.persistence.EntityManager} which allowed 
 * all sql operations.
 * 
 * @param name The name from the EntityManager.
 * @return The EntityManager.
 */
public EntityManager getEntityManager(String name);
```


```java
/**
 * Create a database with the specific parameter in the folder
 * <code>System.getProperty("user.dir") + File.separator + 
 * "database"</code> if it not exists.<br />
 * If the parameter have no suffix <code>.odb</code> then it will be
 * automaticaly added, otherwise not.
 * 
 * @param database The name for the database which should be created.
 */
public void register(String database);
```


```java
/**
 * Remove a {@link com.github.naoghuman.lib.database.api.ICrudService} with the 
 * given name. Also the associated {@link javax.persistence.EntityManager} will 
 * be removed.
 * 
 * @param name The name for the <code>ICrudService</code> which should be removed.
 * @see com.github.naoghuman.lib.database.api.ILibDatabase#removeEntityManager(java.lang.String)
 */
public void removeCrudService(String name);
```


```java
/**
 * Remove a {@link javax.persistence.EntityManager} with the given name. Also
 * the associated {@link com.github.naoghuman.lib.database.api.ICrudService} 
 * will be removed.
 * 
 * @param name The name for the <code>EntityManager</code> which should be removed.
 * @see com.github.naoghuman.lib.database.api.ILibDatabase#removeCrudService(java.lang.String)
 */
public void removeEntityManager(String name);
```


```java
/**
 * Close the previous registered database.
 * 
 * @see com.github.naoghuman.lib.database.api.ILibDatabase#register(java.lang.String)
 */
public void shutdown();
```



Download<a name="Download" />
---

Current `version` is `0.4.1`. Main points in this release are:
* This is a minor update.
* Primarily the section `Examples` in the README is extended.

**Maven coordinates**  
```xml
<dependencies>
    <dependency>
        <groupId>com.github.naoghuman</groupId>
        <artifactId>lib-database-objectdb</artifactId>
        <version>0.4.1</version>
    </dependency>
    <dependency>
        <groupId>com.github.naoghuman</groupId>
        <artifactId>lib-logger</artifactId>
        <version>0.4.1</version>
    </dependency>
</dependencies>
```

Download:
* [Release v0.4.1 (05.22.2017)]

An overview about all existings releases can be found here:
* [Overview from all releases in Lib-Database-ObjectDB]



Requirements<a name="Requirements" />
---

* On your system you need [JRE 8] or [JDK 8] installed.
* The library [Lib-Database-ObjectDB-0.4.01.jar](#Installation).
  * Included is the [objectdb-2.7.1_01.jar].
  * Included is the [javax.persistence-2.1.1.jar].
  * Included is the [javax.transaction-1.1.jar].

In the library are following libraries registered as dependencies:
* The library [Lib-Logger-0.4.1.jar](#Installation).
  * Included in `Lib-Logger` is the library [log4j-api-2.8.2.jar].
  * Included is `Lib-Logger` is the library [log4j-core-2.8.2.jar].


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
[UML-diagram_Lib-Database-ObjectDB_v0.5.0_2017-07-09_19-21]:https://user-images.githubusercontent.com/8161815/27996146-0b8b4310-64dc-11e7-9f68-41967874fd32.png



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
[javax.persistence-2.1.1.jar]:http://search.maven.org/#artifactdetails|org.eclipse.persistence|javax.persistence|2.1.1|jar
[javax.transaction-1.1.jar]:http://mvnrepository.com/artifact/javax.transaction/jta
[JDK 8]:http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[JRE 8]:http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
[Lib-Database-ObjectDB]:https://github.com/Naoghuman/lib-database-objectdb
[Lib-Logger]:https://github.com/Naoghuman/lib-logger
[log4j-api-2.8.2.jar]:https://logging.apache.org/log4j/2.0/log4j-web/dependencies.html
[log4j-core-2.8.2.jar]:https://logging.apache.org/log4j/2.0/log4j-web/dependencies.html
[Maven]:http://maven.apache.org/
[NetBeans]:https://netbeans.org/
[ObjectDB]:http://www.objectdb.com/
[objectdb-2.7.1_01.jar]:http://www.objectdb.com/object/db/database/download
[Overview from all releases in Lib-Database-ObjectDB]:https://github.com/Naoghuman/lib-database-objectdb/releases
[Pull Request]:https://help.github.com/articles/using-pull-requests
[Release v0.4.1 (05.22.2017)]:https://github.com/Naoghuman/lib-database-objectdb/releases/tag/v0.4.1


