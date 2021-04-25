package theaterengine.entity;

import java.awt.Graphics2D;

import theaterengine.core.World;

/**
 * @author hundun
 * Created on 2021/04/01
 */
public interface IGameObject {
    void updateGame(World world);
    void postUpdateGame(World world);
    void renderGame(Graphics2D g2d, World world);
}
