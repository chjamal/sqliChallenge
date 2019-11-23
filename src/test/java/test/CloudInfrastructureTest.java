package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * L’objectif de cet exercice est de construire un outil léger pour gérer une infrastructure cloud.
 * On se limitera dans cet outil de gérer des espaces de stockage (comme Google Drive), et des
 * machines virtuelles (comme Google Cloud Platform), via quelques opérations simple de gestion.
 *
 * The goal of this exercise is to build a lightweight tool to handle a cloud infrastructure. We
 * will limit ourselves in this tool to manage storages (like Google Drive), and virtual machines
 * (like Google Cloud Platform), using some simple management operations.
 *
 *
 */
public class CloudInfrastructureTest {

    /**
     * This is the main Cloud class. You will need to create it and implement it. The cloud class
     * can also call other classes that you can create if needed.
     */
    private CloudInfrastructure cloud = new CloudInfrastructure();

    /**
     * Create Store in the cloud, identified by its name. Upload documents in that storage.
     *
     * the method cloud.listStores() display all existing stores with all their content.
     */
    @Test
    public void can_create_a_store_in_cloud() {
        cloud.createStore("myFiles");
        cloud.uploadDocument("myFiles", "book.pdf"); // upload "book.pdf" in the "myFiles" store

        String expected = "myFiles:book.pdf";
        assertEquals(expected, cloud.listStores()); // make sure the cloud.listStores() method
        // return the expected String
    }

    /**
     * We can create multiple stores in the cloud, and upload several documents to each one of them.
     */
    @Test
    public void can_create_multiple_stores_in_cloud() {
        cloud.createStore("myFiles");
        cloud.createStore("myImages");
        cloud.uploadDocument("myImages", "picture.jpeg", "profile.png");

        String expected = "myFiles:empty||myImages:picture.jpeg, profile.png"; // an empty store is display as "empty"
        assertEquals(expected, cloud.listStores());
    }

    /**
     * We can also delete or empty a store. When a store does not contain any documents, "empty" is
     * displayed.
     */
    @Test
    public void can_delete_and_empty_stores_in_cloud() {
        cloud.createStore("myFiles");
        cloud.createStore("myImages");
        cloud.uploadDocument("myImages", "picture.jpeg", "profile.png");

        cloud.deleteStore("myFiles"); // delete a store
        assertEquals("myImages:picture.jpeg, profile.png", cloud.listStores());

        cloud.emptyStore("myImages"); // empty a store
        assertEquals("myImages:empty", cloud.listStores()); // an empty store is display as "empty"
    }

    /**
     * The creation of a store with a name that is already used will throw the CreateStoreException.
     */
    @Test(expected = CreateStoreException.class)
    public void cannot_create_stores_with_same_names() {
        cloud.createStore("myFiles");
        cloud.createStore("myFiles"); // will throw the exception
    }

    /**
     * We move here to the second part of the test, virtual machines (VM). We can create several VMs
     * in the cloud. Each VM can have three possible statuses : Inactive, Running or Stopped. A new
     * VM is always Inactive at its creation. We can then start or stop it.
     */
    @Test
    public void create_machines() {
        // create a new machine takes 4 parameters : name, operating system, disk size, memory.
        cloud.createMachine("machine1", "Linux", "50gb", "8gb");
        cloud.createMachine("machine2", "Windows", "20gb", "4gb");

        // Remember, all machines are inactive by default.
        assertEquals("machine1:inactive||machine2:inactive", cloud.listMachines());


        cloud.startMachine("machine1"); // start the machine "machine1"
        assertEquals("machine1:running||machine2:inactive", cloud.listMachines());

        cloud.startMachine("machine2");
        cloud.stopMachine("machine1"); // stop machine "machine1"
        assertEquals("machine1:stopped||machine2:running", cloud.listMachines());
    }


}
