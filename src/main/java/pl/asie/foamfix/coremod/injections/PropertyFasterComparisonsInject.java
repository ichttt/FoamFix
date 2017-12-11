/*
 * Copyright (C) 2016, 2017 Adrian Siekierka
 *
 * This file is part of FoamFix.
 *
 * FoamFix is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FoamFix is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FoamFix.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Additional permission under GNU GPL version 3 section 7
 *
 * If you modify this Program, or any covered work, by linking or
 * combining it with the Minecraft game engine, the Mojang Launchwrapper,
 * the Mojang AuthLib and the Minecraft Realms library (and/or modified
 * versions of said software), containing parts covered by the terms of
 * their respective licenses, the licensors of this Program grant you
 * additional permission to convey the resulting work.
 */

package pl.asie.foamfix.coremod.injections;

import com.google.common.base.Optional;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyHelper;

import java.util.Collection;

public class PropertyFasterComparisonsInject {
    // TODO: Staticify allowedValues as it's the same
    public static class Bool extends PropertyHelper<Boolean> {
        protected Bool(String name) {
            super(name, Boolean.class);
        }

        public boolean equals(Object other) {
            return other == this || (other instanceof PropertyBool && super.equals(other));
        }

        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public Collection<Boolean> getAllowedValues() {
            return null;
        }

        @Override
        public Optional<Boolean> parseValue(String s) {
            return null;
        }

        @Override
        public String getName(Boolean aBoolean) {
            return null;
        }
    }
}
