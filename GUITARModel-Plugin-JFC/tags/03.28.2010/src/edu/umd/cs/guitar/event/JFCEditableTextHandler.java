/*	
 *  Copyright (c) 2009-@year@. The GUITAR group at the University of Maryland. Names of owners of this group may
 *  be obtained by sending an e-mail to atif@cs.umd.edu
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 *  documentation files (the "Software"), to deal in the Software without restriction, including without 
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *	the Software, and to permit persons to whom the Software is furnished to do so, subject to the following 
 *	conditions:
 * 
 *	The above copyright notice and this permission notice shall be included in all copies or substantial 
 *	portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 *	LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 *	EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 *	IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 *	THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package edu.umd.cs.guitar.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleEditableText;

import edu.umd.cs.guitar.model.GComponent;
import edu.umd.cs.guitar.util.Debugger;
import edu.umd.cs.guitar.util.GUITARLog;

/**
 * @author <a href="mailto:baonn@cs.umd.edu"> Bao Nguyen </a>
 */
public class JFCEditableTextHandler extends JFCEventHandler {

	/**
     * 
     */
	public JFCEditableTextHandler() {
//		for(int i =0;i<255*255;i++)
//			GUITAR_DEFAULT_TEXT += Character.toString((char)(i));
	}

	/**
     * 
     */
	private static String GUITAR_DEFAULT_TEXT = "GUITAR DEFAULT TEXT: "  
	+"!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~"
	+"¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþ"
	+"ÿĀāĂăĄąĆćĈĉĊċČčĎďĐđĒēĔĕĖėĘęĚěĜĝĞğĠġĢģĤĥĦħĨĩĪīĬĭĮįİıĲĳĴĵĶķĸĹĺĻļĽľĿŀŁłŃńŅņŇňŉŊŋŌōŎŏŐőŒœŔŕŖŗŘřŚśŜŝŞşŠšŢţŤťŦŧŨũŪūŬŭŮůŰűŲųŴŵŶŷŸŹźŻżŽžſƀƁƂƃƄƅƆƇƈƉƊƋƌƍƎƏƐƑƒƓƔƕƖƗƘƙƚƛƜƝƞƟƠơƢƣƤƥƦƧƨƩƪƫƬƭƮƯưƱƲƳƴƵƶƷƸƹƺƻƼƽƾƿǀǁǂǃ"
	+"ǍǎǏǐǑǒǓǔǕǖǗǘǙǚǛǜǝǞǟǠǡǢǣǤǥǦǧǨǩǪǫǬǭǮǯǰ"
	+"ǾǿȀȁȂȃȄȅȆȇȈȉȊȋȌȍȎȏȐȑȒȓȔȕȖȗȘșȚțȜȝȞȟȠȡȢȣȤȥȦȧȨȩȪȫȬȭȮȯȰȱȲȳȴȵȶȷȸȹȺȻȼȽȾȿɀɁɂɃɄɅɆɇɈɉɊɋɌɍɎɏɐɑɒɓɔɕɖɗɘəɚɛɜɝɞɟɠɡɢɣɤɥɦɧɨɩɪɫɬɭɮɯɰɱɲɳɴɵɶɷɸɹɺɻɼɽɾɿʀʁʂʃʄʅʆʇʈʉʊʋʌʍʎʏʐʑʒʓʔʕʖʗʘʙʚʛʜʝʞʟʠʡʢʣʤʥʦʧʨʩʪʫʬʭʮʯʰʱʲʳʴʵʶʷʸʹ"
	;
	//";

	@Override
	public void performImpl(GComponent gComponent) {

		List<String> args = new ArrayList<String>();
		args.add(GUITAR_DEFAULT_TEXT);
		performImpl(gComponent, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.umd.cs.guitar.event.AbstractEventHandler#actionPerformImp(com.sun
	 * .star.accessibility.XAccessible, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void performImpl(GComponent gComponent, Object parameters) {
		
		

		if (gComponent == null) {
			return;
		}

		if (parameters instanceof List) {

			List<String> lParameter = (List<String>) parameters;
			String sInputText;
			if (lParameter == null) {
				sInputText = GUITAR_DEFAULT_TEXT;
			} else
				sInputText = (lParameter.size() != 0) ? lParameter.get(0)
						: GUITAR_DEFAULT_TEXT;

			Accessible aComponent = getAccessible(gComponent);
			AccessibleContext aContext = aComponent.getAccessibleContext();
			AccessibleEditableText aTextEvent = aContext
		
			.getAccessibleEditableText();
			
					

			if (aTextEvent == null) {
				System.err.println(this.getClass().getName()
						+ " doesn't support");
				return;
			}
			try {
				aTextEvent.setTextContents(sInputText);
			} catch (Exception e) {
				try {
					Method setText = aComponent.getClass().getMethod("setText",
							String.class);
					setText.invoke(aComponent, sInputText);

					Debugger.pause(GUITAR_DEFAULT_TEXT);	
					
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					GUITARLog.log.error(e1);
				} catch (NoSuchMethodException e1) {
					GUITARLog.log.error(e1);
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					GUITARLog.log.error(e1);
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					GUITARLog.log.error(e1);
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					GUITARLog.log.error(e1);
				}
			}

		}
	}

}
