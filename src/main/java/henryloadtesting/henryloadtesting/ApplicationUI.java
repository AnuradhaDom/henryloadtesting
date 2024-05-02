package henryloadtesting.henryloadtesting;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringUI
@Theme("${appName.toLowerCase()}Theme")
@Title("Vaading + Springboot")
public class ApplicationUI extends UI {

    @Override
    protected void init(VaadinRequest request) {

        Label label = new Label("Welcome to Vaadin + Springboot");
        label.setStyleName(ValoTheme.LABEL_H1);

        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(label);
        layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);

        setContent(layout);
    }
}