/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity {


    int quantity = 0;
    int price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the increment button is clicked.
     */

    public void increment(View view) {
        if (quantity == 10) {
            Toast.makeText(this, "Not more than 10 please", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        display(quantity);
    }

    /**
     * This method is called when the decrement button is clicked.
     */

    public void decrement(View view) {

        if (quantity <= 1) {
            Toast.makeText(getApplicationContext(), "Why na... WHY!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {

        EditText getName = (EditText) findViewById(R.id.customer_name);
        String customerName = getName.getText().toString();

        CheckBox hasWhippedCreamState = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = hasWhippedCreamState.isChecked();

        CheckBox hasChocolateState = (CheckBox) findViewById(R.id.choco_late);
        boolean hasChocolate = hasChocolateState.isChecked();


        calculatePrice(quantity, hasWhippedCream, hasChocolate);
        String fullSummary = creatOrderSummary(price, hasWhippedCream, hasChocolate, customerName);

        android.content.Intent sendSummary = new android.content.Intent();
        sendSummary.setAction(android.content.Intent.ACTION_SEND);
        sendSummary.putExtra(android.content.Intent.EXTRA_EMAIL, "coffeehouse@website.com");
        sendSummary.putExtra(android.content.Intent.EXTRA_TEXT, fullSummary);
        sendSummary.setType("text/plain");

        // Verify that the intent will resolve to an activity
        if (sendSummary.resolveActivity(getPackageManager()) != null) {
            startActivity(sendSummary);
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */

    private void display(int number) {
        TextView text_view = (TextView) findViewById(R.id.quantity_text_view);
        text_view.setText(String.valueOf(number));
    }


    /**
     * Calculates the price of the order.
     * <p>
     * is the number of cups of coffee ordered
     */
    private void calculatePrice(int quantity, boolean whippedcream, boolean chocolate) {

        int withWhippedCream = 0;
        int withChocolate = 0;
        if (chocolate) {
            withChocolate = quantity * 2;
        }

        if (whippedcream) {
            withWhippedCream = quantity * 1;
        }

        int coffeePrice = quantity * 5;
        price = coffeePrice + withWhippedCream + withChocolate;
    }

    /**
     * My new method to update the price displayed to show the name and total
     * <p>
     * the price will be an argument passed in
     */
    public String creatOrderSummary(int price, boolean Whippedcream, boolean Chololate, String cosName) {
        String fullSummary = "Name: " + cosName;
        fullSummary += "\nWants Whipped cream ? " + Whippedcream;
        fullSummary += "\nWants Chocolate ? " + Chololate;
        fullSummary += "\nQuantity: " + quantity;
        fullSummary += "\nTotal: " + NumberFormat.getCurrencyInstance().format(price);
        fullSummary += "\nThank you!";
        return fullSummary;
    }
}
