import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactManagementSystemTest {

    @BeforeEach
    void startFresh() {
        ContactManagementSystem.names = new String[10];
        ContactManagementSystem.phones = new String[10];
        ContactManagementSystem.emails = new String[10];
        ContactManagementSystem.contactGroups = new String[10];
        ContactManagementSystem.favourites = new boolean[10];
        ContactManagementSystem.addresses = new String[10];
        ContactManagementSystem.birthdays = new String[10];
        ContactManagementSystem.companies = new String[10];
        ContactManagementSystem.notes = new String[10];
        ContactManagementSystem.contactCount = 0;
        ContactManagementSystem.groupNames = new String[10];
        ContactManagementSystem.groupCount = 0;
    }

    
    public void addSimple(String name, String phone) {
        ContactManagementSystem.addContact(name, phone, "", "", false, "", "", "", "");
    }

    // ---------- addContact ----------

    @Test
    void addContact_increasesCount() {
        addSimple("Ada", "08031234567");

        assertEquals(1, ContactManagementSystem.contactCount);
    }

    @Test
    void addContact_storesNameAndPhone() {
        addSimple("Ada", "08031234567");

        assertEquals("Ada", ContactManagementSystem.names[0]);
        assertEquals("08031234567", ContactManagementSystem.phones[0]);
    }

    @Test
    void addContact_worksForMoreThanTenContacts() {
        for (int i = 0; i < 11; i++) {
            addSimple("Name" + i, "0800000000" + i);
        }

        assertEquals(11, ContactManagementSystem.contactCount);
        assertEquals("Name10", ContactManagementSystem.names[10]);
    }

    // ---------- findPhone ----------

    @Test
    void findPhone_returnsIndexOfContact() {
        addSimple("Ada", "0801111111");
        addSimple("Bola", "0802222222");

        assertEquals(1, ContactManagementSystem.findPhone("0802222222"));
    }

    @Test
    void findPhone_returnsMinusOneWhenNotFound() {
        addSimple("Ada", "0801111111");

        assertEquals(-1, ContactManagementSystem.findPhone("0809999999"));
    }

    // ---------- removeContactAt ----------

    @Test
    void removeContactAt_reducesCount() {
        addSimple("Ada", "0801111111");
        addSimple("Bola", "0802222222");

        ContactManagementSystem.removeContactAt(0);

        assertEquals(1, ContactManagementSystem.contactCount);
    }

    @Test
    void removeContactAt_movesNextContactsLeft() {
        addSimple("Ada", "0801111111");
        addSimple("Bola", "0802222222");
        addSimple("Chidi", "0803333333");

        ContactManagementSystem.removeContactAt(1);

        assertEquals("Ada", ContactManagementSystem.names[0]);
        assertEquals("Chidi", ContactManagementSystem.names[1]);
        assertEquals("0803333333", ContactManagementSystem.phones[1]);
    }

    // ---------- swapContacts ----------

    @Test
    void swapContacts_swapsNameAndPhone() {
        addSimple("Ada", "0801111111");
        addSimple("Bola", "0802222222");

        ContactManagementSystem.swapContacts(0, 1);

        assertEquals("Bola", ContactManagementSystem.names[0]);
        assertEquals("0802222222", ContactManagementSystem.phones[0]);
        assertEquals("Ada", ContactManagementSystem.names[1]);
        assertEquals("0801111111", ContactManagementSystem.phones[1]);
    }

    // ---------- sortContacts ----------

    @Test
    void sortContacts_putsNamesInAlphabeticalOrder() {
        addSimple("Chidi", "0803333333");
        addSimple("Ada", "0801111111");
        addSimple("Bola", "0802222222");

        ContactManagementSystem.sortContacts();

        assertEquals("Ada", ContactManagementSystem.names[0]);
        assertEquals("Bola", ContactManagementSystem.names[1]);
        assertEquals("Chidi", ContactManagementSystem.names[2]);
    }

    @Test
    void sortContacts_keepsPhoneWithTheRightName() {
        addSimple("Chidi", "0803333333");
        addSimple("Ada", "0801111111");

        ContactManagementSystem.sortContacts();

        assertEquals("Ada", ContactManagementSystem.names[0]);
        assertEquals("0801111111", ContactManagementSystem.phones[0]);
    }

    // ---------- groups ----------

    @Test
    void findGroup_ignoresUpperAndLowerCase() {
        ContactManagementSystem.addGroupName("Family");

        assertEquals(0, ContactManagementSystem.findGroup("family"));
    }

    @Test
    void findGroup_returnsMinusOneWhenNotFound() {
        ContactManagementSystem.addGroupName("Family");

        assertEquals(-1, ContactManagementSystem.findGroup("Gym"));
    }
}
