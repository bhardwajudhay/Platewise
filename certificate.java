package com.example.platewise;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class certificate extends AppCompatActivity {
    private TextView certificateMessage;
    private static final int REQUEST_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate);

        certificateMessage = findViewById(R.id.certificateMessage);

        // Get user name from intent and display message
        String userName = getIntent().getStringExtra("USER_NAME");

        // Set certificate message
        if (userName != null) {
            certificateMessage.setText("Thank you, " + userName + "!\nYour food information has been successfully added to Platewise.");
        } else {
            certificateMessage.setText("Thank you for using Platewise!");
        }

        // Check and request permissions for writing to storage
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {
            // Generate PDF after permission granted
            generatePdf(userName != null ? userName : "User");
        }
    }

    // Handle the result of the permissions request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permissions granted, generate PDF
            generatePdf(getIntent().getStringExtra("USER_NAME"));
        } else {
            Toast.makeText(this, "Permission denied, unable to generate PDF", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to generate PDF certificate
    private void generatePdf(String userName) {
        // Run the PDF generation task in the background
        new GeneratePdfTask().execute(userName);
    }

    // AsyncTask to generate the PDF in background thread
    private class GeneratePdfTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String userName = params[0];

            try {
                // Create the directory to store the PDF if not exists
                File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "PlatewiseCertificates");
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // File name
                String fileName = "Certificate_" + userName + ".pdf";
                File pdfFile = new File(directory, fileName);

                // Create a FileOutputStream
                FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);

                // Create a PdfWriter instance
                PdfWriter writer = new PdfWriter(fileOutputStream);

                // Create a PdfDocument instance
                PdfDocument pdf = new PdfDocument(writer);

                // Create a Document object
                Document document = new Document(pdf);

                // Add content to the PDF
                document.add(new Paragraph("Food Waste Management Certificate"));
                document.add(new Paragraph("\n"));
                document.add(new Paragraph("Thank you, " + userName + "!"));
                document.add(new Paragraph("Your food information has been successfully added to Platewise."));

                // Close the document
                document.close();

                return "PDF Created: " + pdfFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
                return "Error generating PDF: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Notify the user that the PDF is created or show error
            Toast.makeText(certificate.this, result, Toast.LENGTH_LONG).show();
        }
    }

}
