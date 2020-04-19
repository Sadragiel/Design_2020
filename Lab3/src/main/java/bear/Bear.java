package bear;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Bear {
    Color bodyColor;
    Color shirtColor;

    public Bear() {
        this.bodyColor = Color.rgb(201, 154, 102);
        this.shirtColor = Color.rgb(198, 55, 51);
    }


    public void draw(Group group) {
        group.getChildren().add(getRightLeg());
        group.getChildren().add(getShirtBack());
        group.getChildren().add(getLeftArm());
        group.getChildren().addAll(getBody());
        group.getChildren().add(getShirtBase());
        group.getChildren().addAll(getLeftLeg());
        group.getChildren().add(getRightArm());
        group.getChildren().addAll(getHead());
        group.getChildren().addAll(getCloses());
    }

    private Node getRightLeg() {
        Path rightLeg = new Path(
            new MoveTo(62, 169),
            new QuadCurveTo(42, 163, 14, 170),
            new QuadCurveTo(6, 168, 5, 158),
            new LineTo(6, 132),
            new QuadCurveTo(25, 118, 27, 141),
            new QuadCurveTo(42, 131, 56, 133)
        );
        rightLeg.setFill(this.bodyColor);
        rightLeg.setStroke(Color.BLACK);
        return rightLeg;
    }

    private Node[] getLeftLeg() {
        Node[] leftLeg = new Node[3];

        Path leg = new Path(
            new MoveTo(49, 185),
            new CubicCurveTo(48, 178, 68, 174, 71, 188),
            new QuadCurveTo(105, 152, 122, 172),
            new CubicCurveTo(143, 195, 57, 228, 52, 222)
        );
        leg.setFill(this.bodyColor);
        leg.setStroke(Color.BLACK);

        Ellipse foot = new Ellipse(51, 203, 5, 19);
        foot.setFill(bodyColor);
        foot.setStroke(Color.BLACK);

        QuadCurve additionalFootCircuit = new QuadCurve(71, 188, 74, 191, 68, 210);
        additionalFootCircuit.setStroke(Color.BLACK);
        additionalFootCircuit.setFill(Color.TRANSPARENT);

        leftLeg[0] = leg;
        leftLeg[1] = additionalFootCircuit;
        leftLeg[2] = foot;
        return leftLeg;
    }

    private Node getRightArm() {
        Path rightArm = new Path(
            new MoveTo(197, 169),
            new CubicCurveTo(217, 165, 214, 191, 188, 183),
            new QuadCurveTo(177, 179, 167, 134),
            new LineTo(195, 127),
            new LineTo(202, 168)
        );
        rightArm.setFill(this.bodyColor);
        rightArm.setStroke(Color.BLACK);
        return rightArm;
    }

    private Node getLeftArm() {
        Path leftArm = new Path(
                new MoveTo(88, 103),
                new LineTo(97, 95),
                new LineTo(103, 103)
        );
        leftArm.setFill(this.bodyColor);
        leftArm.setStroke(Color.BLACK);
        return leftArm;
    }

    private Node[] getBody() {
        Node[] body = new Node[2];

        Circle stomach = new Circle(95, 145, 40, this.bodyColor);
        stomach.setStroke(Color.BLACK);

        Path back = new Path(
            new MoveTo(64, 170),
            new QuadCurveTo(90, 210, 135, 185),
            new QuadCurveTo(157, 172, 165, 155),
            new LineTo(118, 102),
            new QuadCurveTo(95, 94, 72, 112)
        );
        back.setFill(this.bodyColor);
        back.setStroke(Color.BLACK);

        body[0] = stomach;
        body[1] = back;
        return body;
    }

    private Node[] getHead() {
        Node[] head = new Node[14];

        Path headBase = new Path(
                new MoveTo(127, 102),
                new QuadCurveTo(94, 67, 102, 56),
                new LineTo(118, 48),
                new QuadCurveTo(115, 40, 110, 38),
                new QuadCurveTo(108, 32, 113, 30),
                new QuadCurveTo(130, 30, 147, 18),
                new QuadCurveTo(182, 5, 193, 55),
                new CubicCurveTo(196, 64, 186, 73, 193, 84)
        );
        headBase.setFill(this.bodyColor);
        headBase.setStroke(Color.BLACK);

        Circle leftEar = new Circle(142, 16, 7, this.bodyColor);
        leftEar.setStroke(Color.BLACK);

        Path rightEar = new Path(
                new MoveTo(172, 24),
                new QuadCurveTo(162, 14, 173, 5),
                new QuadCurveTo(193, 2, 182, 34)
        );
        rightEar.setFill(bodyColor);
        rightEar.setStroke(Color.BLACK);

        Circle leftEye = new Circle(121, 42, 1.5, Color.BLACK);
        Circle rightEye = new Circle(141, 39, 1.5, Color.BLACK);

        QuadCurve leftEyebrow = new QuadCurve(116, 37, 113, 31, 120, 32);
        leftEyebrow.setFill(Color.TRANSPARENT);
        leftEyebrow.setStroke(Color.BLACK);

        QuadCurve rightEyebrow = new QuadCurve(138, 27, 147, 23, 150, 30);
        rightEyebrow.setFill(Color.TRANSPARENT);
        rightEyebrow.setStroke(Color.BLACK);

        QuadCurve noseBridge = new QuadCurve(125, 48, 128, 42, 124, 36);
        noseBridge.setFill(Color.TRANSPARENT);
        noseBridge.setStroke(Color.BLACK);

        QuadCurve additionalNoseBridgeCircuit = new QuadCurve(140, 46, 143, 40, 149, 41);
        additionalNoseBridgeCircuit.setFill(Color.TRANSPARENT);
        additionalNoseBridgeCircuit.setStroke(Color.BLACK);

        QuadCurve mouth = new QuadCurve(143, 58, 123, 93, 110, 62);
        mouth.setFill(Color.TRANSPARENT);
        mouth.setStroke(Color.BLACK);

        Line mouthCorner = new Line(146, 57, 141, 56);
        mouthCorner.setFill(Color.TRANSPARENT);
        mouthCorner.setStroke(Color.BLACK);

        QuadCurve chin = new QuadCurve(120, 78, 122, 84, 127, 86);
        chin.setFill(Color.TRANSPARENT);
        chin.setStroke(Color.BLACK);

        QuadCurve nose = new QuadCurve(110, 62, 107, 42, 128, 49);
        nose.setFill(Color.TRANSPARENT);
        nose.setStroke(Color.BLACK);

        CubicCurve noseTip = new CubicCurve(114, 60, 101, 51, 114, 39, 120, 52);
        noseTip.setFill(Color.BLACK);

        head[0] = leftEar;
        head[1] = headBase;
        head[2] = rightEar;
        head[3] = leftEye;
        head[4] = rightEye;
        head[5] = leftEyebrow;
        head[6] = rightEyebrow;
        head[7] = additionalNoseBridgeCircuit;
        head[8] = noseBridge;
        head[9] = mouth;
        head[10] = mouthCorner;
        head[11] = chin;
        head[12] = nose;
        head[13] = noseTip;
        return head;
    }

    private Node getShirtBack() {
        Polyline shirtBack = new Polyline(167, 169, 156, 166, 168, 149);
        shirtBack.setFill(shirtColor);
        shirtBack.setStroke(Color.BLACK);
        return shirtBack;
    }

    private Node getShirtBase() {
        Path shirtBase = new Path(
                new MoveTo(95, 100),
                new CubicCurveTo(124, 92, 150, 148, 166, 168),
                new QuadCurveTo(166, 156, 172, 151),
                new LineTo(203, 125),
                new CubicCurveTo(188, 110, 207, 94, 194, 91),
                new QuadCurveTo(209, 75, 187, 75),
                new LineTo(106, 75),
                new QuadCurveTo(108, 84, 94, 90),
                new QuadCurveTo(97, 95, 98, 100)
        );
        shirtBase.setFill(shirtColor);
        shirtBase.setStroke(Color.BLACK);
        return shirtBase;
    }

    private Node[] getCloses() {
        Node[] shirt = new Node[6];

        Path collar = new Path(
                new MoveTo(196, 77),
                new QuadCurveTo(203, 85, 172, 83),
                new CubicCurveTo(153, 79, 126, 113, 119, 94),
                new LineTo(118, 101),
                new LineTo(158, 139)
        );
        collar.setFill(shirtColor);
        collar.setStroke(Color.BLACK);

        Polyline collarCircuit = new Polyline(118, 97, 118, 101, 158, 139);
        collarCircuit.setStroke(shirtColor);
        collarCircuit.setStrokeWidth(2);

        Path sleeve = new Path(
                new MoveTo(161, 108),
                new QuadCurveTo(163, 119, 167, 121),
                new LineTo(166, 134),
                new CubicCurveTo(179, 142, 193, 124, 203, 125)
        );
        sleeve.setFill(shirtColor);
        sleeve.setStroke(Color.BLACK);

        QuadCurve sleeveCircuit = new QuadCurve(161, 94, 171, 88, 190, 88);
        sleeveCircuit.setFill(Color.TRANSPARENT);
        sleeveCircuit.setStroke(Color.BLACK);

        QuadCurve crease1 = new QuadCurve(113, 86, 110, 88, 110, 95);
        crease1.setFill(Color.TRANSPARENT);
        crease1.setStroke(Color.BLACK);

        QuadCurve crease2 = new QuadCurve(116, 95, 101, 93, 99, 98);
        crease2.setFill(Color.TRANSPARENT);
        crease2.setStroke(Color.BLACK);

        shirt[0] = collar;
        shirt[1] = collarCircuit;
        shirt[2] = sleeveCircuit;
        shirt[3] = sleeve;
        shirt[4] = crease1;
        shirt[5] = crease2;
        return shirt;
    }

}
