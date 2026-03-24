export interface BorrowRequestCreate {
  itemId: string;
}

export interface BorrowRequestResponse {
  id: string;
  itemTitle: string;
  requesterUsername: string;
  status: string; // 'PENDING' | 'APPROVED' | 'REJECTED' | 'RETURNED'
}
