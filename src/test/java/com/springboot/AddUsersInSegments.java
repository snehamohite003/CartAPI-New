package com.springboot;

import com.springboot.controller.SegmentResponse;

public class AddUsersInSegments {
	
	
	
	public static SegmentResponse getSegmentResponse(int userid) {
		SegmentResponse segmentResponse = new SegmentResponse();

		switch (userid) {
		case 1:
			segmentResponse.setSegment("p1");
			break;
		case 2:
			segmentResponse.setSegment("p2");
			break;
		case 3:
			segmentResponse.setSegment("p3");
			break;
		}

		return segmentResponse;
	}

}
