import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-Notification-dialog',
  templateUrl: './Notification-dialog.component.html',
  styleUrls: ['./Notification-dialog.component.scss']
})
export class NotificationDialogComponent {
  title: string;
  message: string;

  constructor(public dialogRef: MatDialogRef<NotificationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: NotificationDialogModel) {
    this.title = data.title;
    this.message = data.message;
  }

  onNotificationOk(): void {
    this.dialogRef.close(true);
  }

 
}

/**
 * Class to represent Notification dialog model.
 *
 * It has been kept here to keep it as part of shared component.
 */
export class NotificationDialogModel {

  constructor(public title: string, public message: string) {
  }
}
