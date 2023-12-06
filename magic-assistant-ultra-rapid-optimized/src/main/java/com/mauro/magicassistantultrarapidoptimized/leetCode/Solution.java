package com.mauro.magicassistantultrarapidoptimized.leetCode;

public class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode();
        ListNode head = result;

        int carryOver = 0;
        int temp = 0;

        while (l1 != null && l2 != null)
        {
            temp = l1.val + l2.val + carryOver;
            carryOver = temp / 10;
            temp = temp % 10;

            result.next = new ListNode(temp);
            result = result.next;

            l1 = l1.next;
            l2 = l2.next;
        }

        while (l1 != null)
        {
            temp = l1.val + carryOver;
            carryOver = temp / 10;
            temp = temp % 10;

            result.next = new ListNode(temp);
            result = result.next;

            l1 = l1.next;
        }

        while (l2 != null)
        {
            temp = l2.val + carryOver;
            carryOver = temp / 10;
            temp = temp % 10;

            result.next = new ListNode(temp);
            result = result.next;

            l2 = l2.next;
        }

        if (carryOver == 1)
        {
            result.next = new ListNode(1);
        }

        return head.next;
    }
}