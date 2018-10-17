package org.guoqing.functional.collection;

import org.guoqing.functional.utils.Option;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ${DESCRIPTION}
 *
 * @author dengguoqing
 * @date 2018-10-18
 */
public class MapOption<T,U> {
    private final ConcurrentMap<T, U> map = new ConcurrentHashMap<>();

    public static <T,U> MapOption<T,U> empty(){
        return new MapOption<>();
    }

    public static <T,U> MapOption<T,U> add(MapOption<T,U> m,T t,U u){
        m.map.put(t, u);
        return m;
    }

    public Option<U> get(final T t){
        return this.map.containsKey(t) ? Option.some(this.map.get(t)) :
                Option.none();
    }

    public MapOption<T,U> put(T t,U u){
        return add(this, t, u);
    }

    public MapOption<T,U> removeKey(T t){
        this.map.remove(t);
        return this;
    }
}

