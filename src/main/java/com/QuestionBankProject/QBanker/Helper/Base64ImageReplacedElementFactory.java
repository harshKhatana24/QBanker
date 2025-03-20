package com.QuestionBankProject.QBanker.Helper;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

import java.io.IOException;
import java.util.Base64;

public class Base64ImageReplacedElementFactory implements ReplacedElementFactory {

    private final ReplacedElementFactory defaultFactory;

    public Base64ImageReplacedElementFactory(ReplacedElementFactory defaultFactory) {
        this.defaultFactory = defaultFactory;
    }


    public ReplacedElement createReplacedElement(LayoutContext layoutContext, BlockBox blockBox,
                                                 Element element, UserAgentCallback userAgentCallback,
                                                 int cssWidth, int cssHeight) {
        if ("img".equalsIgnoreCase(element.getNodeName())) {
            String src = element.getAttribute("src");

            if (src != null && src.startsWith("data:image")) {
                try {
                    String base64Data = src.substring(src.indexOf(",") + 1);
                    byte[] decodedBytes = Base64.getDecoder().decode(base64Data);

                    Image image = Image.getInstance(decodedBytes);

                    if (cssWidth > 0 || cssHeight > 0) {
                        // Consider scaling to points if necessary (e.g., cssWidth * 72f / 96)
                        image.scaleAbsolute(cssWidth, cssHeight);
                    }

                    FSImage fsImage = new ITextFSImage(image);
                    return new ITextImageElement(fsImage);
                } catch (IOException | BadElementException e) {
                    e.printStackTrace();
                    // Fall back to default handling on error
                }
            }
        }
        // Delegate to default factory for non-data images or errors
        return defaultFactory.createReplacedElement(layoutContext, blockBox, userAgentCallback, cssWidth, cssHeight);
    }

    @Override
    public ReplacedElement createReplacedElement(LayoutContext layoutContext, BlockBox blockBox,
                                                 UserAgentCallback userAgentCallback, int cssWidth, int cssHeight) {
        // Delegate to default factory for this overload
        return defaultFactory.createReplacedElement(layoutContext, blockBox, userAgentCallback, cssWidth, cssHeight);
    }

    @Override
    public void reset() {
        defaultFactory.reset();
    }

    @Override
    public void remove(Element element) {
        defaultFactory.remove(element);
    }

    @Override
    public void setFormSubmissionListener(FormSubmissionListener listener) {
        defaultFactory.setFormSubmissionListener(listener);
    }
}