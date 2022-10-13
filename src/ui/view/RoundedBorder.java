package ui.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Graphics2D;

import javax.swing.border.Border;

class RoundedBorder implements Border 
{
    private int r;

    RoundedBorder(int r) {
        this.r = r;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.r+1, this.r+1, this.r+2, this.r);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width-1, height-1, r, r);

        if(g instanceof Graphics2D){
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.RED);
        }
    }
}