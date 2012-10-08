/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.maths.highlevelapi.functions.DOGMAFunctions.DOGMAArithmetic;

import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

import com.opengamma.maths.commonapi.exceptions.MathsExceptionNullPointer;
import com.opengamma.maths.highlevelapi.datatypes.primitive.OGDoubleArray;
import com.opengamma.maths.highlevelapi.functions.DOGMAFunctionCollection.DOGMAArithmetic;

/**
 * Tests OGDoubleArray transpose
 */
public class DOGMAOGDoubleArrayTransposeTest {

  DOGMAArithmetic DA = new DOGMAArithmetic();

  int normalRows = 4;
  int normalCols = 3;
  double[] _data = new double[] {1, 4, 7, 10, 2, 5, 8, 11, 3, 6, 9, 12 };
  OGDoubleArray array1 = new OGDoubleArray(_data, normalRows, normalCols);


  int transposedRows = 3;
  int transposedCols = 4;
  double[] _dataTransposed = new double[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };  
  OGDoubleArray array1tranposed = new OGDoubleArray(_dataTransposed, transposedRows, transposedCols);
  
  @Test(expectedExceptions = MathsExceptionNullPointer.class)
  public void nullInTest() {
    OGDoubleArray tmp = null;
    DA.transpose(tmp);
  }

  @Test
  public void testTranspose() {
    assertTrue(array1tranposed.equals(DA.transpose(array1)));
  }

}
