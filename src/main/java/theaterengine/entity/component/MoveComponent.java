package theaterengine.entity.component;

import theaterengine.entity.BaseItem;

/**
 * @author hundun
 * Created on 2021/04/02
 */
public class MoveComponent {
    Double targetX;
    Double targetY;
    double speedOfPixelPerFrame;
    
    BaseItem parent;
    
    public MoveComponent(BaseItem parent) {
        this.parent = parent;
    }
    
    public void setTarget(Double additionX, Double additionY, double speed) {
        this.targetX = additionX != null ? (parent.x + additionX) : null;
        this.targetY = additionY != null ? (parent.y + additionY) : null;
        this.speedOfPixelPerFrame = speed;
    }
    
    public boolean needMove() {
        return targetX != null || targetY != null;
    }
    
    public void moveClockArrive() {
        boolean isXArrival;
        boolean isYArrival;
        
        if (targetX != null) {
            boolean isXIncreasing = parent.x < targetX;
            if (isXIncreasing) {
                parent.x  += speedOfPixelPerFrame;
                isXArrival = parent.x >= targetX;
            } else {
                parent.x  -= speedOfPixelPerFrame;
                isXArrival = parent.x <= targetX;
            }
        } else {
            isXArrival = true;
        }
        
        if (targetY != null) {
            boolean isYIncreasing = parent.y < targetY;
            if (isYIncreasing) {
                parent.y += speedOfPixelPerFrame;
                isYArrival = parent.y >= targetY;
            } else {
                parent.y -= speedOfPixelPerFrame;
                isYArrival = parent.y <= targetY;
            }
        } else {
            isYArrival = true;
        }
        
        if (isXArrival && isYArrival) {
           parent.x = targetX != null ? targetX : parent.x;
           parent.y = targetY != null ? targetY : parent.y;
           targetX = null;
           targetY = null;
        }
    }
    
    
    
    
}
