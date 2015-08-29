Lib-Database-ObjectDB
=====================



Intention
---------

Lib-Database-ObjectDB is a library for easy accessing an [ObjectDB] database in 
a [JavaFX] &amp; [Maven] desktop application.

Current `version` is `0.0.7` (08.2015).



Content
-------

* [Examples](#Examples)
    - [de.pro.lib.database.LibDatabaseTest#registerWithSuffix()](#RegisterWithSuffix)
    - [de.pro.lib.database.CountEntityTest#count()](#Count)
* [Api](#Api)
    - [de.pro.lib.database.api.ICrudService](#ICrudService)
    - [de.pro.lib.database.api.DatabaseFacade](#DatabaseFacade)
* [Download](#Download)
* [Requirements](#Requirements)
* [Installation](#Installation)
* [Documentation](#Documentation)
* [Contribution](#Contribution)
* [License](#License)
* [Autor](#Autor)
* [Contact](#Contact)



Examples<a name="Examples" />
-------

### de.pro.lib.database.LibDatabaseTest#registerWithSuffix()<a name="RegisterWithSuffix" />

```java
private final static String TEST_DB_WITH_SUFFIX = "test4.odb"; // NOI18N

@Test
public void registerWithSuffix() {
    DatabaseFacade.INSTANCE.register(TEST_DB_WITH_SUFFIX);
    
    // The database (if not exitst) will only created if an transaction is done
    DatabaseFacade.INSTANCE.getCrudService("registerWithSuffix").create(new CountEntity());
   
    DatabaseFacade.INSTANCE.shutdown();
    
    File file = new File(DATABASE_PATH + TEST_DB_WITH_SUFFIX);
    assertTrue("The database test.odb must exists...", file.exists());
    
    DatabaseFacade.INSTANCE.drop(TEST_DB_WITH_SUFFIX);
    assertFalse("The database test.odb must deleted...", file.exists());
}
```


### de.pro.lib.database.CountEntityTest#count()<a name="Count" />

```java
private final static String TABLE = "CountEntity"; // NOI18N

@Test
public void count() {
    LoggerFacade.INSTANCE.getLogger().own(this.getClass(), " #count()");

    Long count = DatabaseFacade.INSTANCE.getCrudService("count").count(TABLE);
    assertTrue("count must -1", count.longValue()==-1);
        
    final CountEntity ce = DatabaseFacade.INSTANCE.getCrudService("count").create(new CountEntity());
    DatabaseFacade.INSTANCE.getCrudService().delete(CountEntity.class, new Long(ce.getId()));
    count = DatabaseFacade.INSTANCE.getCrudService("count").count(TABLE);
    assertTrue("count must 0", count.longValue()==0);
        
    DatabaseFacade.INSTANCE.getCrudService("count").create(new CountEntity());
    count = DatabaseFacade.INSTANCE.getCrudService("count").count(TABLE);
    assertTrue("count must 1", count.longValue()==1);
        
    DatabaseFacade.INSTANCE.getCrudService("count").create(new CountEntity());
    count = DatabaseFacade.INSTANCE.getCrudService("count").count(TABLE);
    assertTrue("count must 2", count.longValue()==2);
}
```



Api<a name="Api" />
-------

### de.pro.lib.database.api.ICrudService<a name="ICrudService" />

```java
/**
 * The <code>Interface</code> for the class {@link de.pro.lib.database.CrudService}.<br />
 * A common <code>Interface</code> for all CRUD-Component implementations. The
 * type of the entity is specified in the implementation.
 *
 * @author PRo
 * @see de.pro.lib.database.CrudService
 * @see de.pro.lib.database.api.DatabaseFacade
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
 * <li>if <code>isSingleTransaction == true</code> then {@link de.pro.lib.database.CrudService#beginTransaction()}</li>
 * <li>{@link javax.persistence.EntityManager#persist(java.lang.Object)}</li>
 * <li>{@link javax.persistence.EntityManager#flush() }</li>
 * <li>{@link javax.persistence.EntityManager#refresh(java.lang.Object) }</li>
 * <li>if <code>isSingleTransaction == true</code> then {@link de.pro.lib.database.CrudService#commitTransaction()}</li>
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


### de.pro.lib.database.api.DatabaseFacade<a name="DatabaseFacade" />

```java
/**
 * The facade {@link de.pro.lib.database.api.DatabaseFacade} provides access to
 * the action methods during the Interface {@link de.pro.lib.database.api.ILibDatabase}.
 *
 * @author PRo
 * @see de.pro.lib.database.api.ILibDatabase
 */
public enum DatabaseFacade implements ILibDatabase
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
 * Returns a {@link de.pro.lib.database.api.ICrudService} with the name 
 * DEFAULT which allowed all sql operations.
 * 
 * @return The crud service.
 */
public ICrudService getCrudService();
```


```java
/**
 * Returns a named {@link de.pro.lib.database.api.ICrudService} which allowed 
 * all sql operations.
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
 * Remove a {@link de.pro.lib.database.api.ICrudService} with the given name. Also
 * the associated {@link javax.persistence.EntityManager} will be removed.
 * 
 * @param name The name for the <code>ICrudService</code> which should be removed.
 * @see de.pro.lib.database.api.ILibDatabase#removeEntityManager(java.lang.String)
 */
public void removeCrudService(String name);
```


```java
/**
 * Remove a {@link javax.persistence.EntityManager} with the given name. Also
 * the associated {@link de.pro.lib.database.api.ICrudService} will be removed.
 * 
 * @param name The name for the <code>EntityManager</code> which should be removed.
 * @see de.pro.lib.database.api.ILibDatabase#removeCrudService(java.lang.String)
 */
public void removeEntityManager(String name);
```


```java
/**
 * Close the previous registered database.
 * 
 * @see de.pro.lib.database.api.ILibDatabase#register(java.lang.String)
 */
public void shutdown();
```



Download<a name="Download" />
--------

Current `version` is `0.0.7`. Main points in this release are:
* Implement in `DatabaseFacade` the interface `ILibDatabase` directly for easier 
  handling.
* Add new section `Api` in the ReadMe.
* Add new section `Download` in the ReadMe.
* Add new section `Intention` in the ReadMe.
* Update the section `Examples` in the ReadMe.

Download:
* [Release v0.0.7 (08.2015)]

An overview about all existings releases can be found here:
* [Overview from all releases in Lib-Database-ObjectDB]



Requirements<a name="Requirements" />
------------

* On your system you need [JRE 8] or [JDK 8] installed.
* The library [Lib-Database-ObjectDB-0.0.6.jar](#Installation).
  * Included is the [objectdb-2.6.3_04.jar].
  * Included is the [javax.persistence-2.1.0.jar].
  * Included is the [javax.transaction-1.1.jar].
* The library [Lib-Logger-0.2.1.jar](#Installation).
  * Included is the [log4j-api-2.3.jar].
  * Included is the [log4j-core-2.3.jar].


Installation<a name="Installation" />
------------

* If not installed download the [JRE 8] or the [JDK 8].
  * Optional: To work better with [FXML] files in a [JavaFX] application 
    download the [JavaFX Scene Builder] under 'Additional Resources'.
* Choose your preferred IDE (e.g. [NetBeans], [Eclipse] or [IntelliJ IDEA]) for 
  development.
* Download or clone [Lib-Database-ObjectDB].
* Download or clone [Lib-Logger].
* Open the projects in your IDE and run them.



Documentation<a name="Documentation" />
-------------

* In section [Api](#Api) you can see the main point to access the functionality 
  in the library.
* For additional information see the [JavaDoc] in the library itself.



Contribution<a name="Contribution" />
------------

* If you find a `Bug` I will be glad if you will report an [Issue].
* If you want to contribute to the project plz fork the project and do a [Pull Request].



License<a name="License" />
-------

The project `Lib-Database-ObjectDB` is licensed under [General Public License 3.0].



Autor<a name="Autor" />
-----

The project `Lib-Database-ObjectDB` is maintained by me, Peter Rogge. See [Contact](#Contact).



Contact<a name="Contact" />
-------

You can reach me under <peter.rogge@yahoo.de>.



[//]: # (Links)
[Eclipse]:https://www.eclipse.org/
[FXML]:http://docs.oracle.com/javafx/2/fxml_get_started/jfxpub-fxml_get_started.htm
[General Public License 3.0]:http://www.gnu.org/licenses/gpl-3.0.en.html
[IntelliJ IDEA]:http://www.jetbrains.com/idea/
[Issue]:https://github.com/Naoghuman/lib-database-objectdb/issues
[JavaDoc]:http://www.oracle.com/technetwork/java/javase/documentation/index-jsp-135444.html
[JavaFX]:http://docs.oracle.com/javase/8/javase-clienttechnologies.htm
[JavaFX Scene Builder]:http://www.oracle.com/technetwork/java/javase/downloads/index.html
[javax.persistence-2.1.0.jar]:http://search.maven.org/#artifactdetails|org.eclipse.persistence|javax.persistence|2.1.0|jar
[javax.transaction-1.1.jar]:http://mvnrepository.com/artifact/javax.transaction/jta
[JDK 8]:http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[JRE 8]:http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
[Lib-Database-ObjectDB]:https://github.com/Naoghuman/lib-database-objectdb
[Lib-Logger]:https://github.com/Naoghuman/lib-logger
[log4j-api-2.3.jar]:https://logging.apache.org/log4j/2.0/log4j-web/dependencies.html
[log4j-core-2.3.jar]:https://logging.apache.org/log4j/2.0/log4j-web/dependencies.html
[Maven]:http://maven.apache.org/
[NetBeans]:https://netbeans.org/
[ObjectDB]:http://www.objectdb.com/
[objectdb-2.6.3_04.jar]:http://www.objectdb.com/object/db/database/download
[Overview from all releases in Lib-Database-ObjectDB]:https://github.com/Naoghuman/lib-database-objectdb/releases
[Pull Request]:https://help.github.com/articles/using-pull-requests
[Release v0.0.7 (08.2015)]:https://github.com/Naoghuman/lib-database-objectdb/releases/tag/v0.0.7


