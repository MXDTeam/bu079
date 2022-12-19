package gui;

import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;
import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.SimpleAttributeSet;
import java.awt.Color;
import java.io.OutputStream;
import javax.swing.JTextArea;
import java.io.PrintStream;

public class LzjStream extends PrintStream
{
    private final JTextArea mainComponent;
    private final int type;
    private final int lineLimit;
    public static final int OUT = 0;
    public static final int ERR = 1;
    public static final int NOTICE = 2;
    public static final int PACKET = 3;
    
    public LzjStream(final OutputStream out, final JTextArea mainComponent) {
        super(out);
        this.mainComponent = mainComponent;
        this.type = 3;
        this.lineLimit = 100;
    }
    
    public LzjStream(final OutputStream out, final JTextArea mainComponent, final int type) {
        super(out);
        this.mainComponent = mainComponent;
        this.type = type;
        this.lineLimit = 100;
    }
    
    public LzjStream(final OutputStream out, final JTextArea mainComponent, final int type, final int lineLimit) {
        super(out);
        this.mainComponent = mainComponent;
        this.type = type;
        this.lineLimit = lineLimit;
    }
    
    @Override
    public void write(final byte[] buf, final int off, final int len) {
        super.write(buf, off, len);
        final String message = new String(buf, off, len);
        final Color col;
        switch (this.type) {
            case 0: {
                col = Color.BLACK;
                break;
            }
            case 1: {
                col = Color.RED;
                break;
            }
            case 2: {
                col = Color.BLUE;
                break;
            }
            case 3: {
                col = Color.GRAY;
                break;
            }
            default: {
                col = Color.BLACK;
                break;
            }
        }
        SwingUtilities.invokeLater((Runnable)new Runnable() {
            @Override
            public void run() {
                final SimpleAttributeSet attrSet = new SimpleAttributeSet();
                StyleConstants.setForeground((MutableAttributeSet)attrSet, col);
                final Document docMain = LzjStream.this.mainComponent.getDocument();
                try {
                    final String[] docMainInfo = docMain.getText(0, docMain.getLength()).split("\r\n");
                    if (docMainInfo.length >= LzjStream.this.lineLimit + 1) {
                        for (int i = 0; i <= docMainInfo.length - LzjStream.this.lineLimit - 1; ++i) {
                            docMain.remove(0, docMainInfo[i].length() + 2);
                        }
                    }
                    docMain.insertString(docMain.getLength(), message, (AttributeSet)attrSet);
                }
                catch (BadLocationException e) {
                    LzjStream.this.mainComponent.setText("输出出错:" + e + "\r\n內容:" + message + "\r\n类型:" + LzjStream.this.type);
                }
            }
        });
    }
    
    public static void cleanMessage(final JTextArea mainComponent) {
        mainComponent.setText("");
    }
}
