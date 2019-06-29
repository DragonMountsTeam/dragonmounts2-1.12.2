/*
 ** 2012 Februar 10
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.client.model.dragon;

import com.TheRPGAdventurer.ROTD.util.math.MathX;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;

import static org.lwjgl.opengl.GL11.*;

/**
 * Extended model renderer with some helpful extra methods.
 * 
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class ModelPart extends AdvancedModelRenderer {
    
    public static boolean renderAxes;
    
    public float renderScaleX = 1;
    public float renderScaleY = 1;
    public float renderScaleZ = 1;
    
    public float preRotateAngleX;
    public float preRotateAngleY;
    public float preRotateAngleZ;
    
    private AdvancedModelBase base;
    private boolean compiled;
    private int displayList;
    
    public ModelPart(AdvancedModelBase base, String name) {
        super(base, name);
        this.base = base;
    }
    
    public ModelPart(AdvancedModelBase base) {
        this(base, null);
    }

    public ModelPart(AdvancedModelBase modelbase, int i, int j) {
        super(modelbase, i, j);
        base = modelbase;
    }

    public ModelPart addChildBox(String name, float xOfs, float yOfs, float zOfs, int width, int length, int height) {
        ModelPart part = new ModelPart(base, boxName);
        part.mirror = mirror;
        part.addBox(name, xOfs, yOfs, zOfs, width, length, height);
        addChild(part);
        
        return part;
    }
    
    public ModelPart setAngles(float x, float y, float z) {
        rotateAngleX = x;
        rotateAngleY = y;
        rotateAngleZ = z;
        
        return this;
    }
    
    public ModelPart setRenderScale(float scaleX, float scaleY, float scaleZ) {
        this.renderScaleX = scaleX;
        this.renderScaleY = scaleY;
        this.renderScaleZ = scaleZ;
        
        return this;
    }
    
    public ModelPart setRenderScale(float scale) {
        return setRenderScale(scale, scale, scale);
    }
    
    private void compileDisplayList(float scale) {
        BufferBuilder vb = Tessellator.getInstance().getBuffer();
        displayList = GLAllocation.generateDisplayLists(1);
        glNewList(displayList, GL_COMPILE);
        cubeList.forEach(cube -> cube.render(vb, scale));
        glEndList();
        compiled = true;
    }
    
    @Override
    public void render(float scale) {
        renderWithRotation(scale);
    }
    
    @Override
    public void renderWithRotation(float scale) {
        // skip if hidden
        if (isHidden || !showModel) {
            return;
        }
        
        // compile if required
        if (!compiled) {
            compileDisplayList(scale);
        }
        
        GlStateManager.pushMatrix();
        
        postRender(scale);
        
        // call display list
        GlStateManager.callList(displayList);
        
        // render child models
        if (childModels != null) {
            childModels.forEach(obj -> obj.render(scale));
        }
        
        GlStateManager.popMatrix();
    }
    
    @Override
    public void postRender(float scale) {
        // skip if hidden
        if (isHidden || !showModel) {
            return;
        }

        // translate
        GlStateManager.translate(rotationPointX * scale, rotationPointY * scale, rotationPointZ * scale);
        
        // rotate
        if (preRotateAngleZ != 0) {
            GlStateManager.rotate(MathX.toDegrees(preRotateAngleZ), 0, 0, 1);
        }
        if (preRotateAngleY != 0) {
            GlStateManager.rotate(MathX.toDegrees(preRotateAngleY), 0, 1, 0);
        }
        if (preRotateAngleX != 0) {
            GlStateManager.rotate(MathX.toDegrees(preRotateAngleX), 1, 0, 0);
        }
        
        if (rotateAngleZ != 0) {
            GlStateManager.rotate(MathX.toDegrees(rotateAngleZ), 0, 0, 1);
        }
        if (rotateAngleY != 0) {
            GlStateManager.rotate(MathX.toDegrees(rotateAngleY), 0, 1, 0);
        }
        if (rotateAngleX != 0) {
            GlStateManager.rotate(MathX.toDegrees(rotateAngleX), 1, 0, 0);
        }

        // scale
        if (renderScaleX != 0 || renderScaleY != 0 || renderScaleZ != 0) {
            GlStateManager.scale(renderScaleX, renderScaleY, renderScaleZ);
        }
    }
}
