package org.spash;

import org.spash.OverlapEvent;
import org.spash.OverlapListener;
import org.spash.Shape;

public class PlayerPushesAllOthers implements OverlapListener {
    private Body player;
    
    public PlayerPushesAllOthers(Body player) {
        this.player = player;
    }

    public void onOverlap(OverlapEvent event) {
        if(event.getBodyA() == player) {
            Shape shapeB = event.getBodyB().getShape();
            shapeB.moveBy(event.getMinTranslation().toVector());            
        } else if(event.getBodyB() == player) {
            Shape shapeA = event.getBodyA().getShape();
            shapeA.moveBy(event.getMinTranslation().flip().toVector());
        }
    }
}
