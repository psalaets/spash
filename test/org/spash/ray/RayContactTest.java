package org.spash.ray;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.spash.Body;
import org.spash.Vector2f;

public class RayContactTest {
    @Test
    public void ContactsWithDifferentRayAreNotEqual() {
        Body sharedBody = mock(Body.class);
        Ray ray1 = new Ray(new Vector2f(0, 0), new Vector2f(2, 2));
        RayContact contact1 = new RayContact(ray1, sharedBody, new Vector2f(1, 1));
        Ray ray2 = new Ray(new Vector2f(0, 0), new Vector2f(2, 2));
        RayContact contact2 = new RayContact(ray2, sharedBody, new Vector2f(1, 1));

        assertFalse(contact1.equals(contact2));
    }

    @Test
    public void ContactsWithDifferentBodyAreNotEqual() {
        Ray sharedRay = new Ray(new Vector2f(0, 0), new Vector2f(2, 2));
        RayContact contact1 = new RayContact(sharedRay, mock(Body.class), new Vector2f(1, 1));
        RayContact contact2 = new RayContact(sharedRay, mock(Body.class), new Vector2f(1, 1));

        assertFalse(contact1.equals(contact2));
    }

    @Test
    public void ContactsWithDifferentLocationAreNotEqual() {
        Ray sharedRay = new Ray(new Vector2f(0, 0), new Vector2f(3, 3));
        Body sharedBody = mock(Body.class);
        RayContact contact1 = new RayContact(sharedRay, sharedBody, new Vector2f(1, 1));
        RayContact contact2 = new RayContact(sharedRay, sharedBody, new Vector2f(2, 2));

        assertFalse(contact1.equals(contact2));
    }

    @Test
    public void ContactsAreNotEqualToNonContacts() {
        Ray ray = new Ray(new Vector2f(0, 0), new Vector2f(2, 2));
        RayContact contact = new RayContact(ray, mock(Body.class), new Vector2f(1, 1));

        assertFalse(contact.equals("blah"));
    }

    @Test
    public void ContactsAreNotEqualToNull() {
        Ray ray = new Ray(new Vector2f(0, 0), new Vector2f(2, 2));
        RayContact contact = new RayContact(ray, mock(Body.class), new Vector2f(1, 1));

        assertFalse(contact.equals(null));
    }

    @Test
    public void ContactsWithEqualLocationSameRaySameBodyAreEqual() {
        Ray sharedRay = new Ray(new Vector2f(0, 0), new Vector2f(3, 3));
        Body sharedBody = mock(Body.class);
        RayContact contact1 = new RayContact(sharedRay, sharedBody, new Vector2f(1, 1));
        RayContact contact2 = new RayContact(sharedRay, sharedBody, new Vector2f(1, 1));

        assertEquals(contact1, contact2);
        assertEquals(contact2, contact1);
    }

    @Test
    public void ContactsAreEqualToSelf() {
        Ray ray = new Ray(new Vector2f(0, 0), new Vector2f(2, 2));
        RayContact contact = new RayContact(ray, mock(Body.class), new Vector2f(1, 1));

        assertEquals(contact, contact);
    }

    @Test
    public void EqualContactsHaveEqualHashCodes() {
        Ray ray = new Ray(new Vector2f(0, 0), new Vector2f(2, 2));
        Body sharedBody = mock(Body.class);
        RayContact contact1 = new RayContact(ray, sharedBody, new Vector2f(1, 1));
        RayContact contact2 = new RayContact(ray, sharedBody, new Vector2f(1, 1));

        assertEquals(contact1.hashCode(), contact2.hashCode());
    }

    @Test
    public void HashCodeDoesNotChangeOverMultipleCalls() {
        Ray ray = new Ray(new Vector2f(0, 0), new Vector2f(2, 2));
        RayContact contact = new RayContact(ray, mock(Body.class), new Vector2f(1, 1));

        assertEquals(contact.hashCode(), contact.hashCode());
    }
    
    @Test
    public void WhenContactIsAtRayStart_DistanceIsZero() {
        Ray ray = new Ray(new Vector2f(0, 0), new Vector2f(2, 2));
        RayContact contact = new RayContact(ray, mock(Body.class), new Vector2f(0, 0));

        assertEquals(0f, contact.distanceFromRayStart());
    }
    
    @Test
    public void WhenContactIsNotAtRayStart_DistanceIsCalculated() {
        Ray ray = new Ray(new Vector2f(0, 0), new Vector2f(2, 2));
        RayContact contact = new RayContact(ray, mock(Body.class), new Vector2f(4, 3));

        assertEquals(5f, contact.distanceFromRayStart());
    }
}
