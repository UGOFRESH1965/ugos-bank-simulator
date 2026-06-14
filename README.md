# UgosBank - Structural Banking Simulation

A console-based object-oriented banking application built natively in Java. This project demonstrates core backend software engineering concepts, specifically focusing on data integrity, state tracking, and defensive programming.

 🛠️ Core Engineering & Security Features

- Data Encapsulation: Critical fields like `balance` and `securityPin` are declared `private`, making it structurally impossible for external scripts to alter user financial states without passing through secure validation channels.
- State-Based Access Control: Implemented an authentication gate requiring a 4-digit PIN verification before processing any ledger transactions (Balance Enquiry, Deposits, or Withdrawals).
- Brute-Force Mitigation: Programmed an automated security latch that tracks `failedAttempts`. If an incorrect PIN is entered 3 consecutive times, the system switches the account state to `isLocked`, triggers a security alert, and forces an immediate application shutdown to prevent further exploitation.
- Data Sanitization: Built strict conditional validations within transaction loops to block malformed inputs, such as negative monetary entries.

 🚀 How to Run Natively

1. Compile the source file:
   ```bash
   javac UgosBank.java
