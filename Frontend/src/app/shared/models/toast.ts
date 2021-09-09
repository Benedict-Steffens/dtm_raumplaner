export class Toast {
  public roomId: number;
  public message: string;
  public headerText: string;
  public cssClasses: string;
  public isDeleteToast: boolean;

  constructor(roomId: number, message: string, headerText: string, cssClasses: string, isDeleteToast: boolean) {
    this.roomId = roomId;
    this.message = message;
    this.headerText = headerText;
    this.cssClasses = cssClasses;
    this.isDeleteToast = isDeleteToast;
  }
}
