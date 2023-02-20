package org.glitter.links.event.trigger;

import lombok.Getter;
import lombok.Setter;
import org.glitter.links.Value;
import org.glitter.links.event.Trigger;

/**
 * @author Xiaohan.Yuan
 * @date 2023/2/20
 */
public class EqualTrigger implements Trigger {

    boolean must;

    boolean status = false;

    @Getter
    @Setter
    Object value;

    /**
     * 如果被执行了一次，该触发器将不会被触发，
     * 如果触发器状态发生变化，该触发器处于被触发状态
     * */
    Boolean execStatus = true;

    public EqualTrigger(boolean must) {
        this.must = must;
    }


    @Override
    public Boolean execStatus() {
        return execStatus;
    }

    @Override
    public Boolean must() {
        return must;
    }

    @Override
    public Boolean getStatus() {
        return status;
    }

    @Override
    public Boolean check(Value value) {
       if (this.status != (this.value == value.get() ||
               this.value.equals(value.asString()))){
           this.execStatus = true;
       }
        this.status = this.value == value.get() ||
                this.value.equals(value.asString());
        return this.status;
    }
}
