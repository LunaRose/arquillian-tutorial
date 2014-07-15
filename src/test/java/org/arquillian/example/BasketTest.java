package org.arquillian.example;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

@RunWith(Arquillian.class)
public class BasketTest
{
    @Deployment
    public static JavaArchive createDeployment() {
        return Deployments.createBasketDeployment();
    }

    @Inject
    Basket basket;

    @Inject
    OrderRepository repo;

    @Test
    @InSequence(1)
    public void place_order_should_add_order() {
        basket.addItem("Dragon Egg");
        basket.addItem("Dragon Food");
        basket.addItem("Fire Extinguisher");
        basket.placeOrder();

        Assert.assertEquals(1, repo.getOrderCount());
        Assert.assertEquals(0, basket.getItemCount());

        basket.addItem("Ray-gun");
        basket.addItem("Spaceship");
        basket.placeOrder();

        Assert.assertEquals(2, repo.getOrderCount());
        Assert.assertEquals(0, basket.getItemCount());
    }

    @Test
    @InSequence(2)
    public void order_should_be_persistent() {
        Assert.assertEquals(2, repo.getOrderCount());
    }
}
