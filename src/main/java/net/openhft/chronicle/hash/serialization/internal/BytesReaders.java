/*
 * Copyright 2014 Higher Frequency Trading
 *
 * http://www.higherfrequencytrading.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.openhft.chronicle.hash.serialization.internal;

import net.openhft.chronicle.hash.serialization.BytesReader;
import net.openhft.lang.io.Bytes;
import net.openhft.lang.io.serialization.BytesMarshaller;

/**
 * Utility methods returning {@link BytesReader} implementations.
 */
public final class BytesReaders {

    /**
     * Returns a {@link BytesReader} wrapping the given {@link BytesMarshaller}. One of the
     * bridge methods between general serialization API from {@link net.openhft.lang} and
     * reader - writer - interop API, specific for ChronicleHashes,
     * from {@link net.openhft.chronicle.hash.serialization} package.
     *
     * @param marshaller the actual reading implementation
     * @param <E>        type of the objects marshalled
     * @return a {@code BytesReader} wrapping the given {@code BytesMarshaller}
     */
    public static <E> BytesReader<E> fromBytesMarshaller(BytesMarshaller<? super E> marshaller) {
        return new SimpleBytesReader<E>(marshaller);
    }

    /**
     * Returns {@code BytesMarshaller} {@code m}, if the given reader is the result of {@link
     * #fromBytesMarshaller(BytesMarshaller) fromBytesMarshaller(m)} call, {@code null} otherwise.
     *
     * @param reader reader to extract {@code BytesMarshaller} from
     * @param <E> type of the objects marshalled
     * @return {@code BytesMarshaller} from which the specified reader was created, or {@code null}
     */
    public static <E> BytesMarshaller<E> getBytesMarshaller(BytesReader<E> reader) {
        return reader instanceof SimpleBytesReader ? ((SimpleBytesReader) reader).marshaller : null;
    }

    private static class SimpleBytesReader<E> implements BytesReader<E> {
        private static final long serialVersionUID = 0L;

        private final BytesMarshaller<? super E> marshaller;

        public SimpleBytesReader(BytesMarshaller<? super E> marshaller) {
            this.marshaller = marshaller;
        }

        @Override
        public E read(Bytes bytes, long size) {
            return (E) marshaller.read(bytes);
        }

        @Override
        public E read(Bytes bytes, long size, E toReuse) {
            return (E) marshaller.read(bytes, toReuse);
        }
    }

    private BytesReaders() {
    }
}
