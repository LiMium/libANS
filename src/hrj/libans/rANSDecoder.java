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
import java.io.InputStream;

public class rANSDecoder extends rANSBaseDecoder {

  public rANSDecoder(final InputStream in, final SymbolStatistics stats) throws IOException {
    super(in);
    this.stats = stats;
  }

  private final SymbolStatistics stats;

  public int advance() throws IOException {
    return super.advance(stats);
  }
}
