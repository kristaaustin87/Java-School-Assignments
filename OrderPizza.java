/*Assignment 12
Krista Delap
April 28, 2018*/

//imports
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.geometry.*;
import java.text.DecimalFormat;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.paint.Color;

public class Assignment12_KDelap extends Application
{
    public static void main(String args[])
    {
        launch();
    }
    
    public void start(Stage myStage)
    {
        myStage.setTitle("Pizza Selection");
        
        FlowPane root = new FlowPane();
        
        Scene scene = new Scene(root, 325, 100);
        
        //create panes
        FlowPane topPane = new FlowPane();
        FlowPane bottomPane = new FlowPane();
        FlowPane pricePane = new FlowPane();
        FlowPane sizeButtons = new FlowPane();
        FlowPane toppingBoxes = new FlowPane();
        
        //make separators
        Separator line1 = new Separator();
        line1.setPrefWidth(350);
        Separator line2 = new Separator();
        line2.setPrefWidth(350);
        
        //to format double values
        DecimalFormat formattedPrice = new DecimalFormat("#.00"); 
        
        //set labels
        Label size = new Label("Pizza Size"); 
        Label top = new Label("Toppings");
        Label price = new Label("Price: ");
        Label showPrice = new Label(formattedPrice.format(5));
        
        //create buttons
        RadioButton small = new RadioButton("Small");
        RadioButton med = new RadioButton("Medium");
        RadioButton lg = new RadioButton("Large");
        //add to toggle groups
        ToggleGroup sizes = new ToggleGroup();
        small.setToggleGroup(sizes);
        med.setToggleGroup(sizes);
        lg.setToggleGroup(sizes);
        
        //select initial
        small.fire();
        
        //set checkboxes
        CheckBox plain = new CheckBox("Plain");
        CheckBox s = new CheckBox("Sausage");
        CheckBox m = new CheckBox("Mushroom");
        CheckBox p = new CheckBox("Pepperoni");
        
        //select plain initially
        plain.setSelected(true);
        
        //set actions for buttons
        small.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent ae)
            {
                showPrice.setText(formattedPrice.format(
                getPizzaPrice(small, med, lg ,plain, s, m, p)));
            }
        });
        
        med.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent ae)
            {
                showPrice.setText(formattedPrice.format(
                getPizzaPrice(small, med, lg ,plain, s, m, p)));
            }
        });
        
        lg.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent ae)
            {
                showPrice.setText(formattedPrice.format(
                getPizzaPrice(small, med, lg ,plain, s, m, p)));
            }
        });
        
        //events for checkboxes
        plain.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent ae)
            {
                //unselect others
                if(plain.isSelected())
                {
                    s.setSelected(false);
                    m.setSelected(false);
                    p.setSelected(false);
                }
                
                showPrice.setText(formattedPrice.format(
                getPizzaPrice(small, med, lg ,plain, s, m, p)));
            }
        });
        
        s.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent ae)
            {
                //deselect plain
                if(s.isSelected())
                {
                    plain.setSelected(false);
                }
                
                showPrice.setText(formattedPrice.format(
                getPizzaPrice(small, med, lg ,plain, s, m, p)));
            }
        });
        
        m.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent ae)
            {
                //deselect plain
                if(m.isSelected())
                {
                    plain.setSelected(false);
                }
                
                showPrice.setText(formattedPrice.format(
                getPizzaPrice(small, med, lg ,plain, s, m, p)));
            }
        });
        
        p.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent ae)
            {
                //deselect plain
                if(p.isSelected())
                {
                    plain.setSelected(false);
                }
                
                showPrice.setText(formattedPrice.format(
                getPizzaPrice(small, med, lg ,plain, s, m, p)));
            }
        });
        
        //add children to panes
        toppingBoxes.getChildren().addAll(plain,s,m,p);
        sizeButtons.getChildren().addAll(small, med, lg);
        topPane.getChildren().addAll(size, sizeButtons);
        bottomPane.getChildren().addAll(top, toppingBoxes);
        pricePane.getChildren().addAll(price, showPrice);
        root.getChildren().addAll(topPane, line1, bottomPane, line2, pricePane);
        
        myStage.setScene(scene);
        
        myStage.show();
    }
    
    public double getPizzaPrice(RadioButton small, RadioButton med, RadioButton lg,
    CheckBox plain, CheckBox s, CheckBox m, CheckBox p)
    {
        double sizePrice = 0;
        double perTop = 0;
        double toppingsPrice = 0;
        
        //set size price
        if(small.isSelected()){
            sizePrice = 5;
            perTop =.5;
        }
        else if(med.isSelected()){
            sizePrice = 8;
            perTop = 1;
        }
        else if(lg.isSelected()){
            sizePrice = 11.5;
            perTop = 1.75;
        }
        
        //add price per topppings
        if(plain.isSelected()){
            toppingsPrice = 0;
        }
        if(s.isSelected()){
            toppingsPrice += perTop;
        }
        if(m.isSelected()){
            toppingsPrice += perTop;
        }
        if(p.isSelected()){
            toppingsPrice += perTop;
        }

        return sizePrice + toppingsPrice;
    }
    
}
