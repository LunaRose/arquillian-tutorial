package org.arquillian.example;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.net.URL;

@RunWith(Arquillian.class)
public class LogicScreenGrapheneTest {

    @Deployment(testable=false)
    public static WebArchive createDeployment() {
        return Deployments.createLoginScreenDeployment();
    }

    @Drone
    private WebDriver browser;

    @ArquillianResource
    private URL deploymentURL;

    @FindBy(id= "loginForm:userName")
    private WebElement userName;

    @FindBy(id= "loginForm:password")
    private WebElement password;

    @FindBy(id = "loginForm:login")
    private WebElement loginButton;

    @FindBy(tagName="li")
    private WebElement facesMessage;

    @FindByJQuery("p:visible")
    private WebElement signedAs;

    @FindBy(css = "input[type=submit]")
    private WebElement whoAmI;

    @Test
    public void should_login_successfully() {
        browser.get(deploymentURL.toExternalForm() + "login.jsf");

        userName.sendKeys("demo");
        password.sendKeys("demo");

        Graphene.guardHttp(loginButton).click();
        Assert.assertEquals("Welcome", facesMessage.getText().trim());

        Graphene.guardAjax(whoAmI).click();
        Assert.assertTrue(signedAs.getText().contains("demo"));
    }
}
