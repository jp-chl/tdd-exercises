package com.jpvr.tddexercises.tdd;

import java.util.*;

public class CustomList<E> implements List<E> {

    private Object[] internal = {};

    @Override
    public boolean isEmpty() {

        return (internal.length == 0);
    } // end boolean isEmpty()

    @Override
    public boolean add(E element) {

        //internal = new Object[] { element };

        Object[] temp = Arrays.copyOf(internal, internal.length + 1);

        temp[internal.length] = element;

        internal = temp;

        return true;
    } // end boolean add(E element)

    @Override
    public int size() {

        return internal.length;
    } // end int size()

    @Override
    public E get(int index) {

        return (E) internal[index];
    } // end E get(int index)


    @Override
    public boolean contains(Object o) {

        for(int i=0; i<internal.length; i++) {

            // Only works with String
            if ( o.equals(get(i)) ) {
                return true;
            }
        } // end iteration

        return false;
    }

    /**
     * Not yet implemented methods
     */
    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }



    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }



    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }
} // end class CustomList
