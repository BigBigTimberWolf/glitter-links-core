package org.glitter.links;

/**
 * @author Xiaohan.Yuan
 * @date 2023/2/9
 */
public class SimpleValue implements Value{
    private  Object value;
    private Long time;

    public SimpleValue(Object value) {
        this.value = value;
    }

    @Override
    public Object get() {
        return value;
    }

    @Override
    public <T> T as(Class<T> type) {
        return (T) value;
    }

    @Override
    public Long getTime() {
        return time;
    }

    @Override
    public Long setTime(Long time) {
        this.time = time;
        return time;
    }

    @Override
    public Value update(Object value) {
        this.value = value;
        return this;
    }
}
