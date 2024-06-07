package test.Model;

import test.Model.Brick;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * Class representing a Cement Brick.
 */
public class CementBrick extends Brick {

    private static final String NAME = "Cement Brick";
    private static final Color DEF_INNER = new Color(147, 147, 147);
    private static final Color DEF_BORDER = new Color(217, 199, 175);
    private static final int CEMENT_STRENGTH = 2;

    private final Crack crack;
    private Shape brickFace;

    /**
     * Constructor to initialize the CementBrick object.
     *
     * @param point the position of the brick
     * @param size the size of the brick
     */
    public CementBrick(Point point, Dimension size) {
        super(NAME, point, size, DEF_BORDER, DEF_INNER, CEMENT_STRENGTH);
        this.crack = new Crack(DEF_CRACK_DEPTH, DEF_STEPS);
        this.brickFace = getBrickFace();
    }

    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos, size);
    }

    @Override
    public boolean setImpact(Point2D point, int dir) {
        if (isBroken()) {
            return false;
        }
        impact();
        if (!isBroken()) {
            crack.makeCrack(point, dir);
            updateBrickFace();
            return false;
        }
        return true;
    }

    @Override
    public Shape getBrick() {
        return brickFace;
    }

    /**
     * Updates the brick face to include the crack.
     */
    private void updateBrickFace() {
        if (!isBroken()) {
            GeneralPath gp = crack.draw();
            gp.append(getBrickFace(), false);
            brickFace = gp;
        }
    }

    @Override
    public void repair() {
        super.repair();
        crack.reset();
        brickFace = getBrickFace();
    }
}
