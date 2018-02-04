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

package hrj.libans;

import java.io.IOException;
import java.io.OutputStream;

public class rANSEncoder extends rANSBaseEncoder {

  public rANSEncoder(OutputStream out, SymbolStatistics stats) {
    super(out);
    this.stats = stats;
  }

  private final SymbolStatistics stats;

  public void append(int c) {
    final int scaleBits = stats.getScaleBits();
    SymbolInfo symbInfo = stats.update(c);
    super.append(scaleBits, symbInfo.start, symbInfo.freq);
  }

  public void finish() throws IOException {
	super.finish();
    stats.finish();
  }

}