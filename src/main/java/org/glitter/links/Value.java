package org.glitter.links;

import java.util.Date;

/**
 * 包装各种值
 * @author Xiaohan.Yuan
 * @date 2023/2/8
 */
public interface Value {

    /**
     * 获取原始值
     *
     * @return 原始值
     */
    Object get();

    /**
     * 转为String
     *
     * @return String的值
     * @see String#valueOf(int)
     */
    default String asString(){
        return get()==null?"":String.valueOf(get());
    };

    default Integer asInt(){
        return as(Integer.class);
    }

    /**
     * 转为Long
     *
     * @return Long值
     */
    default long asLong() {
        return as(Long.class);
    }

    /**
     * 转为Boolean
     *
     * @return Boolean值
     */
    default boolean asBoolean() {
        return Boolean.TRUE.equals(get())
                || "true".equals(get());
    }

    /**
     * 转为Number,由具体的值决定实际的Number类型
     *
     * @return Number
     */
    default Number asNumber() {
        return as(Number.class);
    }

    /**
     * 转为Date类型
     *
     * @return Date
     */
    default Date asDate() {
        return as(Date.class);
    }

    /**
     * 尝试转为指定的类型.可能会抛出{@link  ClassCastException}
     *
     * @param type 类型
     * @param <T>  类型
     * @return 指定类型的值
     */
    <T> T as(Class<T> type);

    /**
     * 获取更新时间
     * */
    Long getTime();

    /**
     * 修正更新时间
     * */
    Long setTime(Long time);

    /**
     * 更新值
     * */
    Value update(Object value);





}
