//package org.arquillian.example;
//
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.Archive;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.spec.WebArchive;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import javax.inject.Inject;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.transaction.UserTransaction;
//import java.util.*;
//
//@RunWith(Arquillian.class)
//public class GamePersistenceTest
//{
//    @Deployment
//    public static Archive<?> createDeployment() {
//        return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Game.class.getPackage()).addAsResource("test-persistence.xml", "META-INF/persistence.xml").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
//    }
//
//    private static final String[] GAME_TITLES = {
//            "Super Mario Brothers",
//            "Mario Kart",
//            "F-Zero"
//    };
//
//    @PersistenceContext
//    EntityManager em;
//
//    @Inject
//    UserTransaction utx;
//
//    @Before
//    public void preparePersistenceTest() throws Exception {
//        clearData();
//        insertData();
//        startTransaction();
//    }
//
//    @After
//    public void commitTransaction() throws Exception {
//        utx.commit();
//    }
//
//    @Test
//    public void shouldFindAllGamesUsingJpqlQuery() throws Exception {
//        String fetchingAllGamesInJpql = "select g from Game g order by g.id";
//
//        System.out.println("Selecting (using JPQL)...");
//
//        List<Game> games = em.createQuery(fetchingAllGamesInJpql, Game.class).getResultList();
//
//        System.out.println("Found " + games.size() + " games (using JPQL):");
//        assertContainsAllGames(games);
//    }
//
//    private static void assertContainsAllGames(Collection<Game> games)
//    {
//        Assert.assertEquals(GAME_TITLES.length, games.size());
//        final Set<String> retrievedGameTitles = new HashSet<String>();
//        for (Game game : games) {
//            System.out.println("* " + game);
//            retrievedGameTitles.add(game.getTitle());
//        }
//        Assert.assertTrue(retrievedGameTitles.containsAll(Arrays.asList(GAME_TITLES)));
//    }
//
//    private void clearData() throws Exception
//    {
//        utx.begin();
//        em.joinTransaction();
//        System.out.println("Dumping old records...");
//        em.createQuery("delete from Game").executeUpdate();
//        utx.commit();
//    }
//
//    private void insertData() throws Exception {
//        utx.begin();
//        em.joinTransaction();
//        System.out.println("Inserting records");
//        for (String title : GAME_TITLES) {
//            Game game = new Game(title);
//            em.persist(game);
//        }
//        utx.commit();
//        em.clear();
//    }
//
//    private void startTransaction() throws Exception {
//        utx.begin();
//        em.joinTransaction();
//    }
//}
