package view.components;

import javax.swing.border.Border;
import java.awt.Component;
import java.awt.Insets;
import java.awt.Graphics;

/**
 * Allows us to use a border radius on JComponents.
 * Reference: <a href="https://stackoverflow.com/questions/25796572/simplest-code-to-round-corners-of-jlabel-in-java">...</a>
 * @author Shawn Grant
 */
public class RoundedBorder implements Border {
    private final int r;

    public RoundedBorder(int r) {
        this.r = r;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.r + 1, this.r + 1, this.r + 2, this.r);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width - 1, height - 1, r, r);
    }
}