package org.jzy3d.plot3d.primitives.axes;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2GL3;

import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.rendering.compat.GLES2CompatUtils;

public class AxeXRectangleAnnotation extends AxeXLineAnnotation implements AxeAnnotation {

    public void drawLineGLES2(Range yrange, Range zrange) {
        GLES2CompatUtils.glBegin(GL2.GL_POLYGON);
        //GLES2CompatUtils.glLineWidth(width);
        GLES2CompatUtils.glColor4f(color.r, color.g, color.b, color.a);
        GLES2CompatUtils.glVertex3f(value, (float)yrange.getMin(), 0);
        GLES2CompatUtils.glVertex3f(value, (float)yrange.getMax(), 0);
        GLES2CompatUtils.glVertex3f(value-width, (float)yrange.getMax(), 0);
        GLES2CompatUtils.glVertex3f(value-width, (float)yrange.getMin(), 0);
        GLES2CompatUtils.glEnd();
    }

    public synchronized void drawLineGL2(GL gl, Range yrange, Range zrange) {
        //gl.getGL2().glLineWidth(width);
        gl.getGL2().glBegin(GL2.GL_POLYGON);
        gl.getGL2().glColor4f(color.r, color.g, color.b, color.a);
        
        applyPolygonModeLine(gl);
        applyPolygonModeFill(gl);
        double ymin = yrange.getMin()-yrange.getRange()/30;
        double ymax = yrange.getMax()+yrange.getRange()/30;
        double z = zrange.getMin()-2;
        
        gl.getGL2().glVertex3d(value-width, ymin, z);
        gl.getGL2().glVertex3d(value-width, ymax, z);
        gl.getGL2().glVertex3d(value, ymax, z);
        gl.getGL2().glVertex3d(value, ymin, z);
        
        //System.out.println("x=" + value +  " w:" + width + " ymin=" + ymin + " ymax=" + ymax);
        gl.getGL2().glEnd();
    }
    
    public enum PolygonMode {
        FRONT, BACK, FRONT_AND_BACK
    }
    PolygonMode polygonMode = PolygonMode.FRONT_AND_BACK;

    protected void applyPolygonModeLine(GL gl) {
        if (gl.isGL2()) {
            switch (polygonMode) {
            case FRONT:
                gl.getGL2().glPolygonMode(GL.GL_FRONT, GL2GL3.GL_LINE);
                break;
            case BACK:
                gl.getGL2().glPolygonMode(GL.GL_BACK, GL2GL3.GL_LINE);
                break;
            case FRONT_AND_BACK:
                gl.getGL2().glPolygonMode(GL.GL_FRONT_AND_BACK, GL2GL3.GL_LINE);
                break;
            default:
                break;
            }
        } else {
            // glPolygonMode does not exist in opengl es ??

            switch (polygonMode) {
            case FRONT:
                GLES2CompatUtils.glPolygonMode(GL.GL_FRONT, GL2GL3.GL_LINE);
                break;
            case BACK:
                GLES2CompatUtils.glPolygonMode(GL.GL_BACK, GL2GL3.GL_LINE);
                break;
            case FRONT_AND_BACK:
                GLES2CompatUtils.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2GL3.GL_LINE);
                break;
            default:
                break;
            }
        }
    }

    protected void applyPolygonModeFill(GL gl) {
        if (gl.isGL2()) {
            switch (polygonMode) {
            case FRONT:
                gl.getGL2().glPolygonMode(GL.GL_FRONT, GL2GL3.GL_FILL);
                break;
            case BACK:
                gl.getGL2().glPolygonMode(GL.GL_BACK, GL2GL3.GL_FILL);
                break;
            case FRONT_AND_BACK:
                gl.getGL2().glPolygonMode(GL.GL_FRONT_AND_BACK, GL2GL3.GL_FILL);
                break;
            default:
                break;
            }
        } else {

            // glPolygonMode does not exist in opengl es ??
            switch (polygonMode) {
            case FRONT:
                GLES2CompatUtils.glPolygonMode(GL.GL_FRONT, GL2GL3.GL_FILL);
                break;
            case BACK:
                GLES2CompatUtils.glPolygonMode(GL.GL_BACK, GL2GL3.GL_FILL);
                break;
            case FRONT_AND_BACK:
                GLES2CompatUtils.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2GL3.GL_FILL);
                break;
            default:
                break;
            }

        }
    }

    protected void polygonOffseFillEnable(GL gl) {
        gl.glEnable(GL.GL_POLYGON_OFFSET_FILL);
        gl.glPolygonOffset(1.0f, 1.0f);
    }

    protected void polygonOffsetFillDisable(GL gl) {
        gl.glDisable(GL.GL_POLYGON_OFFSET_FILL);
    }
}
