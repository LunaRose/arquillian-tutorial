package org.arquillian.example;

import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;


public class Deployments
{
    private static final String WEBAPP_SRC = "src/main/webapp";

    public static JavaArchive createGreeterDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class).addClasses(Greeter.class, PhraseBuilder.class).addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    public static JavaArchive createBasketDeployment() { return ShrinkWrap.create(JavaArchive.class, "test.jar").addClasses(Basket.class, OrderRepository.class, SingletonOrderRepository.class).addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");}

    public static WebArchive createLoginScreenDeployment() { return ShrinkWrap.create(WebArchive.class, "login.war").addClasses(Credentials.class, User.class, LoginController.class).merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class).importDirectory(WEBAPP_SRC).as(GenericArchive.class), "/", Filters.include(".*\\.xhtml$")).addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml").addAsWebInfResource(new StringAsset("<faces-config version=\"2.0\"/>"), "faces-config.xml"); }
}
