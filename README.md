PROJECT OVERVIEW

You are tasked with creating an advanced inventory management system for a small manufacturing company that has outgrown its existing spreadsheet-based system. The organization has been relying on manual entries from a paper system, and now seeks a more robust software solution.

You will design the application based on a provided graphical user interface (GUI) mock-up and a corresponding UML class diagram, which will guide your development process. Additionally, you must adhere to specific business requirements outlined by a systems analyst.

APPLICATION REQUIREMENTS

Your submission must be your own work. Ensure that the total similarity across the submission does not exceed 30%, and that no single source exceeds 10% similarity. Use the originality report as a guide for this purpose.

Please refer to the rubric for evaluation criteria, as it details the expectations for each section of your submission. Note that tasks must be submitted in specified formats and not as cloud links (e.g., Google Docs, OneDrive).

I. User Interface

A. GUI Development

Create a JavaFX application that closely matches the layout in the provided “Software 1 GUI Mock-Up.” You can use JavaFX with or without FXML, or Scene Builder for FXML files. Avoid using Swing.
The GUI should include the following forms: Main form, Add Part form, Modify Part form, Add Product form, and Modify Product form. You may use a single window with multiple views or separate windows for each form.

B. Documentation

Include Javadoc comments for every class member in your code. Specify any logical or runtime errors you fixed, along with future enhancement ideas in your comments.

II. Application Functionality

C. Class Structure

Create classes based on the UML class diagram, incorporating the provided Part class without modifications. Ensure your code includes:
Inheritance
Abstract and concrete classes
Instance and static variables
Instance and static methods

D. Main Form Functionalities

Implement buttons for adding, modifying, and deleting parts and products, including functionality for searching and filtering results.
Provide user feedback through error messages for unsuccessful operations.

E. Part Forms Functionalities

The Add Part form should allow users to input various details, auto-generate a unique part ID, and redirect to the Main form after saving or canceling.
The Modify Part form should prepopulate fields with existing part data for modifications.

F. Product Forms Functionalities

The Add Product form should allow users to input product details, associate parts, and provide the same functionality as the parts forms.
The Modify Product form should allow similar operations, including populating fields with existing data and managing associated parts.

G. Input Validation

Implement checks for input validation, ensuring that minimum and maximum values are logically correct, preventing deletion of associated products, and handling user errors gracefully with informative messages.

H. Documentation Folder

Create a folder containing Javadoc files generated from your code, and indicate its location in your main method header comment.
I. Professional Communication

Ensure your submission is presented clearly and professionally throughout.
