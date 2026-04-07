export interface ChatMessageResponse {
  id: string;
  senderUsername: string;
  content: string;
  createdAt: string;
  isMine: boolean;
}
