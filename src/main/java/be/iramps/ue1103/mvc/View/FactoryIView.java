package be.iramps.ue1103.mvc.View;

import java.security.InvalidParameterException;

public class FactoryIView {
    public static IView createView(String view, IView app) {
        switch (view) {
            case "main":
                return new Main();
            case "status":
                return (IView) new Status(app);
            case "section":
                return (IView) new Section(app);
            default :
                throw new InvalidParameterException("unkown view : " + view);
        }
    }
}
