package tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class MyLibTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     * @return tag attribute, SKIP_BODY
     * @throws JspException couldn't write content
     */
    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().println("Hello World !");
        } catch (IOException e) {
            throw new JspException("I/O Error", e);
        }
        return SKIP_BODY;
    }

}
