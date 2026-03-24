export interface ItemResponse {
  id: string;
  title: string;
  description: string;
  ownerUsername: string;
  status: string; // 'AVAILABLE' | 'IN_USE'
}

export interface ItemRequest {
  title: string;
  description: string;
}
