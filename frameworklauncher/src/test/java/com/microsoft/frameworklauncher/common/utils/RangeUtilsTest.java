// Copyright (c) Microsoft Corporation
// All rights reserved.
//
// MIT License
//
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
// documentation files (the "Software"), to deal in the Software without restriction, including without limitation
// the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
// to permit persons to whom the Software is furnished to do so, subject to the following conditions:
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED *AS IS*, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
// BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
// DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


package com.microsoft.frameworklauncher.common.utils;

import org.apache.avro.generic.GenericData;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.frameworklauncher.common.model.*;

public class RangeUtilsTest {

  @Test
  public void TestRangeUtils() {
    List<Range> testRangeList = new ArrayList<Range>();
    testRangeList.add(Range.newInstance(6, 7));
    testRangeList.add(Range.newInstance(10, 100));
    testRangeList.add(Range.newInstance(3, 5));
    testRangeList.add(Range.newInstance(90, 102));

    List<Range> testRangeList2 = new ArrayList<Range>();
    testRangeList2.add(Range.newInstance(2, 3));
    testRangeList2.add(Range.newInstance(7, 8));
    testRangeList2.add(Range.newInstance(10, 20));

    List<Range> testRangeList3 = RangeUtils.cloneList(testRangeList2);

    List<Range> sortedResult = RangeUtils.SortRangeList(testRangeList);
    Assert.assertEquals(3, sortedResult.get(0).getBegin().intValue());
    Assert.assertEquals(6, sortedResult.get(1).getBegin().intValue());
    Assert.assertEquals(10, sortedResult.get(2).getBegin().intValue());
    Assert.assertEquals(90, sortedResult.get(3).getBegin().intValue());


    List<Range> coalesceResult = RangeUtils.coalesceRangeList(testRangeList);
    Assert.assertEquals(2, coalesceResult.size());
    Assert.assertEquals(3, coalesceResult.get(0).getBegin().intValue());
    Assert.assertEquals(7, coalesceResult.get(0).getEnd().intValue());
    Assert.assertEquals(10, coalesceResult.get(1).getBegin().intValue());
    Assert.assertEquals(102, coalesceResult.get(1).getEnd().intValue());

    List<Range> result = RangeUtils.intersectRangeList(coalesceResult, testRangeList2);
    Assert.assertEquals(3, result.size());
    Assert.assertEquals(3, result.get(0).getBegin().intValue());
    Assert.assertEquals(3, result.get(0).getEnd().intValue());
    Assert.assertEquals(7, result.get(1).getBegin().intValue());
    Assert.assertEquals(7, result.get(1).getEnd().intValue());
    Assert.assertEquals(10, result.get(2).getBegin().intValue());
    Assert.assertEquals(20, result.get(2).getEnd().intValue());

    result = RangeUtils.subtractRange(coalesceResult, testRangeList2);
    Assert.assertEquals(2, result.size());
    Assert.assertEquals(4, result.get(0).getBegin().intValue());
    Assert.assertEquals(6, result.get(0).getEnd().intValue());
    Assert.assertEquals(21, result.get(1).getBegin().intValue());
    Assert.assertEquals(102, result.get(1).getEnd().intValue());


    List<Range> testRangeList7 = new ArrayList<Range>();
    testRangeList7.add(Range.newInstance(80, 80));
    result = RangeUtils.subtractRange(coalesceResult, testRangeList7);
    Assert.assertEquals(3, result.size());
    Assert.assertEquals(3, result.get(0).getBegin().intValue());
    Assert.assertEquals(7, result.get(0).getEnd().intValue());
    Assert.assertEquals(10, result.get(1).getBegin().intValue());
    Assert.assertEquals(79, result.get(1).getEnd().intValue());
    Assert.assertEquals(81, result.get(2).getBegin().intValue());
    Assert.assertEquals(102, result.get(2).getEnd().intValue());


    result = RangeUtils.addRange(sortedResult, testRangeList2);
    Assert.assertEquals(2, result.size());
    Assert.assertEquals(2, result.get(0).getBegin().intValue());
    Assert.assertEquals(8, result.get(0).getEnd().intValue());
    Assert.assertEquals(10, result.get(1).getBegin().intValue());
    Assert.assertEquals(102, result.get(1).getEnd().intValue());


    List<Range> testRangeList4 = new ArrayList<Range>();
    testRangeList4.add(Range.newInstance(2, 3));
    Assert.assertTrue(RangeUtils.fitInRange(testRangeList4, testRangeList3));

    List<Range> testRangeList5 = new ArrayList<Range>();
    testRangeList5.add(Range.newInstance(1, 3));
    Assert.assertTrue(!RangeUtils.fitInRange(testRangeList5, testRangeList3));

    List<Range> testRangeList6 = new ArrayList<Range>();
    testRangeList6.add(Range.newInstance(9, 9));
    Assert.assertTrue(!RangeUtils.fitInRange(testRangeList6, testRangeList3));

    result = RangeUtils.getSubRange(testRangeList3, 1, 0);
    Assert.assertEquals(1, result.size());
    Assert.assertEquals(1, RangeUtils.getValueNumber(result));

    result = RangeUtils.getSubRange(testRangeList3, 3, 0);
    Assert.assertEquals(3, RangeUtils.getValueNumber(result));

    result = RangeUtils.getSubRange(testRangeList3, 3, 10);
    Assert.assertEquals(3, RangeUtils.getValueNumber(result));
    Assert.assertTrue(result.get(0).getBegin() > 10);
    Assert.assertTrue(result.get(0).getEnd() > 10);

    List<Range> testRangeList10 = new ArrayList<Range>();
    testRangeList10.add(Range.newInstance(80, 80));
    testRangeList10.add(Range.newInstance(80, 81));
    testRangeList10.add(Range.newInstance(100, 103));
    int[] expectedResult = {80, 81, 100, 101, 102, 103};
    for (int i = 0; i < RangeUtils.getValueNumber(testRangeList10); i++) {
      Assert.assertEquals(expectedResult[i], RangeUtils.getValue(testRangeList10, i).intValue());
    }
  }
}


