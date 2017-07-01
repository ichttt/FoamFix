package pl.asie.foamfix.coremod.common;

import java.util.*;

/**
 * Methods whose performance we care about:
 * - containsKey, get, values
 * Methods whose performance matters slightly, but we really don't:
 * - put, remove
 * Assumptions we can make:
 * - all keys are non-null
 */
public abstract class FoamyArrayBackedDataManagerMap<V> implements Map<Integer, V> {
    public static class OneEleven<V> extends FoamyArrayBackedDataManagerMap<V> {
        private final HashSet<Integer> keySet = new HashSet<>();

        @Override
        public Set<Integer> keySet() {
            return keySet;
        }

        @Override
        public Set<Entry<Integer, V>> entrySet() {
            HashSet<Entry<Integer, V>> e = new HashSet<>();
            Iterator<Integer> ii = keySet.iterator();
            while (ii.hasNext()) {
                int i = ii.next();
                e.add(new Entry<Integer, V>() {
                    @Override
                    public Integer getKey() {
                        return i;
                    }

                    @Override
                    public V getValue() {
                        return get(i);
                    }

                    @Override
                    public V setValue(V v) {
                        return put(i, v);
                    }
                });
            }
            return e;
        }

        @Override
        protected void addKey(int i) {
            keySet.add(i);
        }

        @Override
        protected void removeKey(int i) {
            keySet.remove(i);
        }
    }

    private final Collection<Object> objects = new ArrayList<>();
    private Object[] keys = new Object[32];
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object o) {
        return get(o) != null;
    }

    public boolean containsKey(int i) {
        return get(i) != null;
    }

    @Override
    public boolean containsValue(Object o) {
        return objects.contains(o);
    }

    @SuppressWarnings("unchecked")
    public V get(int i) {
        return i >= 0 && i < 256 ? (V) keys[i] : null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(Object o) {
        int i = (Integer) o;
        return i >= 0 && i < 256 ? (V) keys[i] : null;
    }

    protected abstract void addKey(int i);
    protected abstract void removeKey(int i);

    @Override
    @SuppressWarnings("unchecked")
    public V put(Integer integer, V v) {
        int i = integer;
        if (i >= 0 && i < 256) {
            if (i >= keys.length) {
                int newSize = keys.length;
                while (newSize <= i) newSize *= 2;

                Object[] newKeys = new Object[newSize];
                System.arraycopy(keys, 0, newKeys, 0, keys.length);
                keys = newKeys;
            }
            Object old = keys[i];
            keys[i] = v;
            if (old == null) {
                addKey(i);
                size++;
            } else {
                objects.remove(old);
            }
            objects.add(v);
            return (V) old;
        } else {
            throw new RuntimeException("EntityDataManager entry outside of the [0, 255] range (" + i + ") was attempted to be added. Please disable EntityDataManager-related optimizations and report this to the FoamFix developers.");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public V remove(Object o) {
        int i = (Integer) o;
        if (i >= 0 && i < 256) {
            Object old = keys[i];
            keys[i] = null;
            removeKey(i);
            objects.remove(old);
            if (old != null) size--;
            return (V) old;
        } else return null;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends V> map) {
        map.forEach(this::put);
    }

    @Override
    public void clear() {
        for (int i : keySet()) {
            keys[i] = null;
        }
        keySet().clear();
        size = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<V> values() {
        return (Collection<V>) objects;
    }
}
