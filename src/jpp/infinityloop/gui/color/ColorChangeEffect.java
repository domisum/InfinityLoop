package jpp.infinityloop.gui.color;

import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;

public class ColorChangeEffect extends Lighting
{

	// INIT
	public ColorChangeEffect(Color color)
	{
		setDiffuseConstant(1.0);
		setSpecularConstant(0.0);
		setSpecularExponent(0.0);
		setSurfaceScale(0.0);

		setLight(new Distant(0, 90, color));
	}

}
