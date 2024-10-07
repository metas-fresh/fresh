import axios from 'axios';
import { apiBasePath } from '../../../constants';
import { unboxAxiosResponse } from '../../../utils';
import { useEffect, useState } from 'react';
import { toUrl } from '../../../utils/index';
import { useDebounce } from '../../../hooks/useDebounce';

const queryDebounceMillis = 300;

export const useProducts = ({ onBarcodeResult }) => {
  const [isLoading, setLoading] = useState(false);
  const [queryString, setQueryStringState] = useState('');
  const [queryStringToSearch, setQueryStringToSearch] = useState('');
  const [products, setProducts] = useState({});

  const setQueryStringToSearchDebounced = useDebounce(setQueryStringToSearch, queryDebounceMillis);
  const setQueryString = (queryStringNew) => {
    setQueryStringState(queryStringNew);
    setQueryStringToSearchDebounced(queryStringNew);
  };

  useEffect(() => {
    let isMounted = true;
    setLoading(true);
    getProducts(queryStringToSearch)
      .then((productsNew) => {
        if (!isMounted) {
          console.log('ignoring getProducts result since the component is no longer mounted', { productsNew });
          return productsNew;
        }

        if (onBarcodeResult && productsNew.barcodeMatched && productsNew.list?.length === 1) {
          const product = productsNew.list[0];
          onBarcodeResult({
            ...product,
            scannedBarcode: queryStringToSearch,
          });
          setQueryString('');
        } else {
          setProducts(productsNew);
        }
      })
      .finally(() => {
        if (!isMounted) return;
        setLoading(false);
      });

    return () => {
      isMounted = false;
    };
  }, [queryStringToSearch]);

  return {
    isLoading,
    isBarcodeMatched: !!products?.barcodeMatched,
    list: products?.list ?? [],
    queryString,
    setQueryString,
  };
};

const getProducts = (query) => {
  const url = toUrl(`${apiBasePath}/pos/products`, { query });
  return axios.get(url).then((response) => unboxAxiosResponse(response));
};
