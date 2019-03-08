# DatasetsSplits
This is a collection of splittings of publicly available Datasets. 
This collection has been created in order to make this splittings publicly available for everyone and for the sake of reproducibility of the experiments we conducted.

## Time-aware Splitting

In order to evaluate time-aware recommender systems in an offline experimental setting, a typical k-folds or hold-out splitting would be ineffective and unrealistic. 
To be as close as possible to an online real scenario we used the fixed-timestamp splitting method mentioned by Campos and _Recommender Systems Handbook_, also used by Bellogin but with a _dataset centered_ base set. 
The basic idea is choosing a single timestamp that represents the moment in which test users are on the platform waiting for recommendations. Their past corresponds to the training set, and the performance is evaluated with data coming from their future. In this work, we select the splitting timestamp that maximizes the number of users involved in the evaluation by setting two constraints: the training set must keep at least 15 ratings, and the test set must contain at least 5 ratings.

## Reference
If you publish research that uses DatasetsSplits please use:
~~~
@inproceedings{Anelli2019Local,
  author    = {Vito Walter Anelli and
               Tommaso Di Noia and
               Eugenio Di Sciascio and
               Azzurra Ragone and
               Joseph Trotta},
  title     = {Local Popularity and Time in top-N Recommendation},
  booktitle = {Proceedings of the 41st European Conference on Information Retrieval, 14th - 18th April 2019, Cologne, Germany},
  year      = {2019}
}
~~~
The full paper describing the overall approach is available here [PDF](https://github.com/vitowalteranelli/DatasetsSplits/blob/master/ECIR2019_paper_306.pdf)


## Credits
This algorithm has been developed by Vito Walter Anelli and Joseph Trotta while working at [SisInf Lab](http://sisinflab.poliba.it) under the supervision of Tommaso Di Noia.  

## Contacts

   Tommaso Di Noia, tommaso [dot] dinoia [at] poliba [dot] it  
   
   Vito Walter Anelli, vitowalter [dot] anelli [at] poliba [dot] it 
   
   Joseph Trotta, joseph [dot] trotta [at] poliba [dot] it 
