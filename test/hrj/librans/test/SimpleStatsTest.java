/*
    This file is part of lib-rANS.
    Copyright 2018 Harshad RJ

    lib-rANS is free software: you can redistribute it and/or modify
    it under the terms of the Lesser GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    lib-rANS is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the Lesser GNU General Public License
    along with lib-rANS.  If not, see <http://www.gnu.org/licenses/>.
*/

package hrj.librans.test;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import hrj.librans.SymbolInfo;
import hrj.librans.SymbolStatistics;

class SimpleStatsTest {
  static final class SimpleStats implements SymbolStatistics {

    @Override
    public SymbolInfo findSymbol(final int cumFreq) {
      for (int s = 0; s <= 256; s++) {
        if ((s * (s + 1) / 2) > cumFreq) {
          final int symb = s - 1;
          return new SymbolInfo(symb + 1, symb * (symb + 1) / 2, symb);
        }
      }
      return new SymbolInfo(1, 0, 0);
    }

    @Override
    public int getScaleBits() {
      return 16;
    }

    @Override
    public SymbolInfo update(final int s) {
      return new SymbolInfo(s + 1, s * (s + 1) / 2, s);
    }

    @Override
    public void finish() {
    }

  }

  @Test
  void testFew() throws IOException {
    for (int i = 1; i < 100; i += 2) {
      TestUtils.testCodec(new SimpleStats(), new SimpleStats(), i, 256, i);
    }
  }

  @Test
  void testThousand() throws IOException {
    TestUtils.testCodec(new SimpleStats(), new SimpleStats(), 1000, 256, 13);
  }

  @Test
  void testTenThousand() throws IOException {
    TestUtils.testCodec(new SimpleStats(), new SimpleStats(), 10000, 256, 13);
  }

  @Test
  void testHundredThousand() throws IOException {
    TestUtils.testCodec(new SimpleStats(), new SimpleStats(), 100*1000, 256, 131);
  }

  @Test
  void testMillion() throws IOException {
    TestUtils.testCodec(new SimpleStats(), new SimpleStats(), 1000*1000, 256, 121);
  }

}