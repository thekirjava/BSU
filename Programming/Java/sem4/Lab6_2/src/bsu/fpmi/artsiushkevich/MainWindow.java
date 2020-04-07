package bsu.fpmi.artsiushkevich;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {
    Text3D text3D;
    PointLight light1;
    PointLight light2;
    ColoringAttributes downPointColorAttributes;
    ColoringAttributes upPointColorAttributes;
    Vector3d downPointPos;
    Vector3d upPointPos;

    MainWindow() {
        this.setTitle("Lab6_2");
        this.setSize(900, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());
        GraphicsConfiguration graphicsConfiguration = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(graphicsConfiguration);
        SimpleUniverse universe = new SimpleUniverse(canvas);
        Vector3d downVector = new Vector3d(0.0f, 0.0f, 1.0f);
        Color3f downColor = new Color3f(downVector);
        Vector3d upVector = new Vector3d(1.0f, 1.0f, 0.0f);
        Color3f upColor = new Color3f(upVector);

        Transform3D transform;

        BranchGroup objRoot = new BranchGroup();


        TransformGroup sceneTransformGroup = new TransformGroup();
        Transform3D t3d = new Transform3D();
        t3d.setScale(0.35);
        sceneTransformGroup.setTransform(t3d);
        objRoot.addChild(sceneTransformGroup);


        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
                100.0);

        Material material = new Material();

        Appearance appearance = new Appearance();


        material.setLightingEnable(true);
        appearance.setMaterial(material);

        Font3D f3d = new Font3D(new Font("Font", Font.BOLD, 1),
                new FontExtrusion());

        text3D = new Text3D(f3d, "Enter text", new Point3f(
                -2.5f, 0.9f, -2.5f));
        text3D.setCapability(Text3D.ALLOW_STRING_WRITE);
        text3D.setCapability(Text3D.ALLOW_STRING_READ);

        Shape3D shape3D = new Shape3D();
        shape3D.setGeometry(text3D);
        shape3D.setAppearance(appearance);

        TransformGroup textTransformGroup = new TransformGroup();


        textTransformGroup.addChild(shape3D);
        sceneTransformGroup.addChild(textTransformGroup);


        // Create transformations for the positional lights
        transform = new Transform3D();
        downPointPos = new Vector3d(0, 0, -5.5);
        transform.set(downPointPos);
        TransformGroup downPointTransformGroup = new TransformGroup(transform);
        sceneTransformGroup.addChild(downPointTransformGroup);


        transform = new Transform3D();
        upPointPos = new Vector3d(0, 1.5, 2.0);
        transform.set(upPointPos);
        TransformGroup upPointTransformGroup = new TransformGroup(transform);
        sceneTransformGroup.addChild(upPointTransformGroup);

        JSlider downPos = new JSlider(-500, 500, 0);
        JSlider upPos = new JSlider(-500, 500, 0);

        downPointColorAttributes = new ColoringAttributes();
        downPointColorAttributes.setCapability(ColoringAttributes.ALLOW_COLOR_WRITE);
        upPointColorAttributes = new ColoringAttributes();
        upPointColorAttributes.setCapability(ColoringAttributes.ALLOW_COLOR_WRITE);
        downPointColorAttributes.setColor(downColor);
        upPointColorAttributes.setColor(upColor);
        Appearance appearanceLight1 = new Appearance();
        Appearance appearanceLight2 = new Appearance();
        appearanceLight1.setColoringAttributes(downPointColorAttributes);
        appearanceLight2.setColoringAttributes(upPointColorAttributes);
        downPointTransformGroup.addChild(new Sphere(0.15f, appearanceLight1));
        upPointTransformGroup.addChild(new Sphere(0.06f, appearanceLight2));

        Point3f lPoint = new Point3f(downPointPos);
        Point3f rPoint = new Point3f(upPointPos);
        Point3f attenuation = new Point3f(1.0f, 0.0f, 0.0f);

        light1 = new PointLight(downColor, lPoint, attenuation);
        light1.setCapability(PointLight.ALLOW_COLOR_WRITE);
        light1.setCapability(PointLight.ALLOW_COLOR_READ);
        light1.setCapability(PointLight.ALLOW_POSITION_WRITE);
        light1.setCapability(PointLight.ALLOW_POSITION_READ);

        light2 = new PointLight(upColor, rPoint, attenuation);
        light2.setCapability(PointLight.ALLOW_COLOR_WRITE);
        light2.setCapability(PointLight.ALLOW_COLOR_READ);
        light2.setCapability(PointLight.ALLOW_POSITION_WRITE);
        light2.setCapability(PointLight.ALLOW_POSITION_READ);

        light1.setInfluencingBounds(bounds);
        light2.setInfluencingBounds(bounds);


        downPointTransformGroup.addChild(light1);
        upPointTransformGroup.addChild(light2);

        objRoot.compile();


        container.add(canvas, BorderLayout.CENTER);
        container.add(downPos, BorderLayout.SOUTH);
        container.add(upPos, BorderLayout.NORTH);

        universe.getViewingPlatform().setNominalViewingTransform();
        universe.getViewer().getView().setBackClipDistance(100.0);

        universe.addBranchGraph(objRoot);

        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    if (!text3D.getString().isEmpty()) {
                        String newText = text3D.getString().substring(0, text3D.getString().length() - 1);
                        text3D.setString(newText);
                    }
                } else if (e.getExtendedKeyCode() == KeyEvent.VK_DOWN) {
                    Color color = JColorChooser.showDialog(MainWindow.this, "Choose color for down light!", Color.black);
                    if (color != null) {
                        light1.setColor(new Color3f(color));
                        downPointColorAttributes.setColor(new Color3f(color));
                    }
                } else if (e.getExtendedKeyCode() == KeyEvent.VK_UP) {
                    Color color = JColorChooser.showDialog(MainWindow.this, "Choose color for up light!", Color.black);
                    if (color != null) {
                        light2.setColor(new Color3f(color));
                        upPointColorAttributes.setColor(new Color3f(color));
                    }
                } else {
                    if (!e.isActionKey()) {
                        String newText = String.valueOf(e.getKeyChar());
                        text3D.setString(text3D.getString() + newText);
                    }
                }

            }
        });
        downPos.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                double x = downPos.getValue() / 100.0;
                downPointPos = new Vector3d(x, 0, -3.5);
                light1.setPosition(new Point3f(downPointPos));
            }
        });
        upPos.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                double x = upPos.getValue() / 100.0;
                upPointPos = new Vector3d(x, 1.5, 2.0);
                light2.setPosition(new Point3f(upPointPos));
            }
        });
    }
}
